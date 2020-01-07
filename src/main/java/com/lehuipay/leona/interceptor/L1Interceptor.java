package com.lehuipay.leona.interceptor;

import com.lehuipay.leona.Const;
import com.lehuipay.leona.contracts.AsymmetricEncryptor;
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

public class L1Interceptor implements Interceptor {

    private final MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");

    public L1Interceptor(SymmetricEncryptor symmEncryptor, AsymmetricEncryptor asymEncryptor, String encryptionAccept) {
        this.symmEncryptor = symmEncryptor;
        this.asymEncryptor = asymEncryptor;
        this.encryptionAccept = encryptionAccept;
    }


    private final SymmetricEncryptor symmEncryptor;

    private final AsymmetricEncryptor asymEncryptor;

    private final String encryptionAccept;

    private String generateAESKey() {
        return CommonUtil.randomStr(Const.SECRET_KEY_LENGTH);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        final String body = InterceptorHelper.requestBody2String(request);

        /**
         * 加密request
         */
        final String aesKey = generateAESKey();
        final String encrypted = symmEncryptor.encrypt(body.getBytes(), aesKey);
        // 加密secretKey
        final byte[] encryptAESKey = asymEncryptor.pubEncode(aesKey);
        // secretKey签名
        byte[] encryptAESKeySignature = asymEncryptor.sign(encryptAESKey);

        request = request.newBuilder()
                .post(RequestBody.create(encrypted, mediaTypeJSON))
                .addHeader(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL, Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L1)
                .addHeader(Const.HEADER_X_LEHUI_ENCRYPTION_ACCEPT, CommonUtil.NVLL(encryptionAccept))
                .addHeader(Const.HEADER_X_LEHUI_ENCRYPTION_KEY, CommonUtil.base64Encode(encryptAESKey))
                .addHeader(Const.HEADER_X_LEHUI_ENCRYPTION_SIGN, CommonUtil.base64Encode(encryptAESKeySignature))
                .build();

        final Response response = chain.proceed(request);

        if (Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L0.equals(encryptionAccept)) {
            return response;
        } else {
            /**
             * 解密response
             */
            String content = response.body().string();
            // AES秘钥验签
            final byte[] key = CommonUtil.base64Decode(CommonUtil.NVLL(response.header(Const.HEADER_X_LEHUI_ENCRYPTION_KEY)));
            final byte[] sign = CommonUtil.base64Decode(CommonUtil.NVLL(response.header(Const.HEADER_X_LEHUI_ENCRYPTION_SIGN)));
            if (!asymEncryptor.verify(key, sign)) {
                throw new LeonaRuntimeException(LeonaErrorCodeEnum.RSA_ENCRYPTION_VERIFY_FAIL);
            }

            final byte[] secretKey = asymEncryptor.priDecode(key);
            final byte[] decryptBody = symmEncryptor.decrypt(content, secretKey);

            return response.newBuilder()
                    .body(ResponseBody.create(decryptBody, mediaTypeJSON))
                    .build();
        }
    }
}
