package com.lehuipay.leona.interceptor;

import com.lehuipay.leona.Const;
import com.lehuipay.leona.contracts.SymmetricEncryptor;
import com.lehuipay.leona.exception.LeonaErrorCodeEnum;
import com.lehuipay.leona.exception.LeonaRuntimeException;
import com.lehuipay.leona.utils.CommonUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.InvalidKeyException;

public class L2Interceptor implements Interceptor {

    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public L2Interceptor(SymmetricEncryptor symmEncryptor, String secretKey, String encryptionAccept) {
        if (CommonUtil.isEmpty(secretKey)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.L2Interceptor, secretKey should not be empty");
        }
        this.symmEncryptor = symmEncryptor;
        this.secretKey = secretKey;
        this.encryptionAccept = encryptionAccept;
    }

    private final SymmetricEncryptor symmEncryptor;

    private final String secretKey;

    private final String encryptionAccept;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = encryptRequest(chain.request());

        final Response response = chain.proceed(request);

        if (isIgnore(response)) {
            return response;
        }

        return decryptBody(response);
    }

    // 加密request
    @NotNull
    private Request encryptRequest(Request request) throws IOException {
        final String body = InterceptorHelper.requestBody2String(request);
        final String encrypted;
        try {
            encrypted = symmEncryptor.encrypt(body.getBytes(), secretKey);
        } catch (InvalidKeyException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.ENCRYPTION_FAIL, e);
        }

        request = request.newBuilder()
                .post(RequestBody.create(encrypted, MEDIA_TYPE_JSON))
                .addHeader(Const.HEADER_ENCRYPTION_LEVEL, Const.HEADER_ENCRYPTION_LEVEL_L2)
                .addHeader(Const.HEADER_ENCRYPTION_ACCEPT, CommonUtil.NVLL(encryptionAccept))
                .build();
        return request;
    }

    // 解密response
    @NotNull
    private Response decryptBody(Response response) throws IOException {
        String content = response.body() == null ? "" : response.body().string();
        final byte[] decryptBody;
        try {
            decryptBody = symmEncryptor.decrypt(content, secretKey.getBytes());
        } catch (InvalidKeyException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.DECRYPTION_FAIL, e);
        }

        return response.newBuilder()
                .body(ResponseBody.create(decryptBody, MEDIA_TYPE_JSON))
                .build();
    }

    // response header X-Lehui-Encryption-Level 不为 L2 则不解密
    private boolean isIgnore(Response response) {
        final String respEncryptLv = CommonUtil.NVLL(response.header(Const.HEADER_ENCRYPTION_LEVEL));
        return !respEncryptLv.equals(Const.HEADER_ENCRYPTION_LEVEL_L2);
    }
}
