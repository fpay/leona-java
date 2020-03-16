package com.lehuipay.leona.interceptor;

import com.lehuipay.leona.Const;
import com.lehuipay.leona.contracts.Signer;
import com.lehuipay.leona.exception.LeonaErrorCodeEnum;
import com.lehuipay.leona.exception.LeonaRuntimeException;
import com.lehuipay.leona.utils.CommonUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.InvalidKeyException;

public class SignInterceptor implements Interceptor {

    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private final String CONTENT_TYPE_STREAM = "application/octet-stream";

    public SignInterceptor(Signer signer, String agentID) {
        this.signer = signer;
        this.agentID = agentID;
    }

    private Signer signer;

    private String agentID;

    private String generateNonce() {
        return CommonUtil.randomStr(Const.NONCE_MIN_LENGTH, Const.NONCE_MAX_LENGTH);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        final String body = InterceptorHelper.requestBody2String(request);

        // request加签
        final String nonce = generateNonce();
        final String sign;
        try {
            sign = signer.sign(body, nonce);
        } catch (InvalidKeyException e) {
            // 由于在okHttp拦截器中无法抛出受检异常, 所以在此处包装为运行时异常, 有待外部调用方捕捉!
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.SIGN_FAIL, e);
        }

        request = request.newBuilder()
                .addHeader(Const.HEADER_AGENTID, agentID)
                .addHeader(Const.HEADER_NONCE, nonce)
                .addHeader(Const.HEADER_SIGNATURE, sign)
                .build();

        final Response response = chain.proceed(request);

        // response验签

        // 下载文件不需要验签
        if (response.header("Content-Type").equals(CONTENT_TYPE_STREAM)) {
            return response;
        }

        String content = response.body() == null ? "" : response.body().string();
        final String respBody = CommonUtil.NVLL(content);
        final String respNonce = CommonUtil.NVLL(response.header(Const.HEADER_NONCE));
        final String respSignature = CommonUtil.NVLL(response.header(Const.HEADER_SIGNATURE));

        try {
            if (!signer.verify(respBody, respNonce, respSignature)) {
                // 验签失败
                throw new LeonaRuntimeException(LeonaErrorCodeEnum.VERIFY_FAIL);
            }
        } catch (InvalidKeyException e) {
            // 由于签名时已用过key, 所以这里理应不会再报出InvalidKeyException
            // 为保险起见, 仍进行了抛出操作
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.VERIFY_FAIL, e);
        }

        return response.newBuilder()
                .body(ResponseBody.create(content, MEDIA_TYPE_JSON))
                .build();
    }
}
