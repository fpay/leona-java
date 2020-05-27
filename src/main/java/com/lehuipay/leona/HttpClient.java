package com.lehuipay.leona;

import com.lehuipay.leona.exception.LeonaErrorCodeEnum;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.exception.LeonaRuntimeException;
import com.lehuipay.leona.interceptor.L1Interceptor;
import com.lehuipay.leona.interceptor.L2Interceptor;
import com.lehuipay.leona.interceptor.SignInterceptor;
import com.lehuipay.leona.model.ErrorMessage;
import com.lehuipay.leona.utils.CommonUtil;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;

public class HttpClient {

    private final MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");

    public HttpClient(Options options) {
        final HMACSigner signer = new HMACSigner(options.getAgentID(), options.getAgentKey());
        final SignInterceptor signInterceptor = new SignInterceptor(signer, options.getAgentID());
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(10));

        // 加密拦截器
        switch (CommonUtil.NVLL(options.getEncryptionLevel())) {
            case Const.HEADER_ENCRYPTION_LEVEL_L1:
                L1Interceptor l1 = new L1Interceptor(new AESEncryptor(),
                        new RSAEnctryptor(options.getAgentPrivateKey(), options.getLehuipayPublicKey()), options.getEncryptionAccept());
                builder.addInterceptor(l1);
                break;
            case Const.HEADER_ENCRYPTION_LEVEL_L2:
                L2Interceptor l2 = new L2Interceptor(new AESEncryptor(), options.getSecretKey(), options.getEncryptionAccept());
                builder.addInterceptor(l2);
                break;
        }

        // 签名拦截器
        client = builder
                .addInterceptor(signInterceptor)
                .build();
    }

    private final OkHttpClient client;

    /**
     * 同步http请求
     *
     * @param method http方法类型
     * @param url    http请求url
     * @param data   httpRequestBody
     * @param clazz  httpResponseBody marshal类型
     * @param <T>    httpResponseBody的泛型类
     * @param <R>    httpRequestBody的泛型类类
     * @return 指定泛型T的返回值
     * @throws IOException    http异常
     * @throws LeonaException 若返回的http code > 300, 则将responseBody中的信息封装为LeonaException
     */
    public <T, R> T request(final String method, final String url, R data, Class<T> clazz) throws LeonaException {
        final Request request = buildRequest(method, url, data);
        try {
            final Response response = client.newCall(request).execute();
            return parseResponse(response, clazz);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        } catch (IOException e) {
            throw new LeonaException(LeonaErrorCodeEnum.HTTP_ERROR, e);
        }
    }

    /**
     * 异步http请求
     *
     * @param method   http方法类型
     * @param url      http请求url
     * @param data     httpRequestBody
     * @param clazz    httpResponseBody marshal类型
     * @param callback 异步请求的callback方法,
     *                 com.lehuipay.leona.Callback接口包含一个方法 void callback(LeonaException e, T data);
     *                 当返回的http code > 300, server返回信息会被包装为LeonaException
     * @param <T>      httpRequestBody的泛型类
     * @param <R>      httpResponseBody的泛型类类
     */
    public <T, R> void request(final String method, final String url, R data, Class<T> clazz, Callback<T> callback) {
        final Request request = buildRequest(method, url, data);

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.callback(new LeonaException(LeonaErrorCodeEnum.HTTP_ERROR, e), null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    callback.callback(null, parseResponse(response, clazz));
                } catch (LeonaException e) {
                    callback.callback(e, null);
                }
            }
        });
    }

    private <T> Request buildRequest(String method, String url, T data) {
        final JsonAdapter adapter  = new Moshi.Builder().build().adapter(data.getClass());
        String requestBody = adapter.toJson(data);
        return new Request.Builder()
                .url(url)
                .method(method, RequestBody.create(requestBody, mediaTypeJSON))
                .build();
    }

    private <T> T parseResponse(Response response, Class<T> clazz) throws IOException, LeonaException {
        String bodyStr = response.body() == null ? "" : response.body().string();

        if (response.isSuccessful()) {
            final JsonAdapter<T> adapter = new Moshi.Builder().build().adapter(clazz);
            return adapter.fromJson(bodyStr);
        } else {
            final JsonAdapter<ErrorMessage> jsonAdapter = new Moshi.Builder().build().adapter(ErrorMessage.class);
            final ErrorMessage errorMessage = jsonAdapter.fromJson(bodyStr);
            throw new LeonaException(errorMessage);
        }
    }

    public <R> void download(final String method, final String url, R data, OutputStream dst) throws LeonaException {
        final Request request = buildRequest(method, url, data);
        try {
            final Response response = client.newCall(request).execute();
            parseResponse2Stream(response, dst);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        } catch (IOException e) {
            throw new LeonaException(LeonaErrorCodeEnum.HTTP_ERROR, e);
        }
    }

    public <R> void download(final String method, final String url, R data, Callback<OutputStream> callback) {
        final Request request = buildRequest(method, url, data);
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.callback(new LeonaException(LeonaErrorCodeEnum.HTTP_ERROR, e), null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    parseResponse2Stream(response, outputStream);
                    callback.callback(null, outputStream);
                } catch (LeonaException e) {
                    callback.callback(e, null);
                }
            }
        });
    }

    private void parseResponse2Stream(Response response, OutputStream dst) throws IOException, LeonaException {
        if (!response.isSuccessful()) {
            final JsonAdapter<ErrorMessage> jsonAdapter = new Moshi.Builder().build().adapter(ErrorMessage.class);
            final ErrorMessage errorMessage = jsonAdapter.fromJson(response.body().string());
            throw new LeonaException(errorMessage);
        }

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        try {
            is = response.body().byteStream();
            while ((len = is.read(buf)) != -1) {
                dst.write(buf, 0, len);
            }
            dst.flush();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
