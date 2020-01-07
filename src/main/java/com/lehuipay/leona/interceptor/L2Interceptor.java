package com.lehuipay.leona.interceptor;

import com.lehuipay.leona.Const;
import com.lehuipay.leona.contracts.SymmetricEncryptor;
import com.lehuipay.leona.utils.CommonUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class L2Interceptor implements Interceptor {

    private final MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");

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
        Request request = chain.request();
        final String body = InterceptorHelper.requestBody2String(request);
        /**
         * request加密
         */
        final String encrypted = symmEncryptor.encrypt(body.getBytes(), secretKey);

        request = request.newBuilder()
                .post(RequestBody.create(encrypted, mediaTypeJSON))
                .addHeader(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL, Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L2)
                .addHeader(Const.HEADER_X_LEHUI_ENCRYPTION_ACCEPT, CommonUtil.NVLL(encryptionAccept))
                .build();

        final Response response = chain.proceed(request);

        if (Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L0.equals(encryptionAccept)) {
            return response;
        } else {
            /**
             * response解密
             */
            String content = response.body().string();
            final byte[] decryptBody = symmEncryptor.decrypt(content, secretKey.getBytes());

            return response.newBuilder()
                    .body(ResponseBody.create(decryptBody, mediaTypeJSON))
                    .build();
        }
    }
}
