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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

public class L1Interceptor implements Interceptor {

    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

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
        Request request = encryptRequest(chain.request());

        final Response response = chain.proceed(request);

        if (isIgnore(response)) {
            return response;
        }

        return decryptResponse(response);
    }

    // 加密request body
    private Request encryptRequest(Request request) throws IOException {
        final String body = InterceptorHelper.requestBody2String(request);

        // 加密request body
        final String aesKey = generateAESKey();
        final String encrypted;
        try {
            encrypted = symmEncryptor.encrypt(body.getBytes(), aesKey);
        } catch (InvalidKeyException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.ENCRYPTION_FAIL, e);
        }

        // 加密AES秘钥
        final byte[] encryptAESKey;
        try {
            encryptAESKey = asymEncryptor.pubEncode(aesKey);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.RSA_PUBLIC_ENCRYPTION_FAIL, e);
        }

        // AES秘钥签名
        byte[] encryptAESKeySignature = new byte[0];
        try {
            encryptAESKeySignature = asymEncryptor.sign(encryptAESKey);
        } catch (InvalidKeyException | SignatureException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.RSA_ENCRYPTION_SIGN_FAIL, e);
        }

        request = request.newBuilder()
                .post(RequestBody.create(encrypted, MEDIA_TYPE_JSON))
                .addHeader(Const.HEADER_ENCRYPTION_LEVEL, Const.HEADER_ENCRYPTION_LEVEL_L1)
                .addHeader(Const.HEADER_ENCRYPTION_ACCEPT, CommonUtil.NVLL(encryptionAccept))
                .addHeader(Const.HEADER_ENCRYPTION_KEY, CommonUtil.base64Encode(encryptAESKey))
                .addHeader(Const.HEADER_ENCRYPTION_SIGN, CommonUtil.base64Encode(encryptAESKeySignature))
                .build();
        return request;
    }

    // 解密response
    private Response decryptResponse(Response response) throws IOException {

        // AES秘钥验签
        final byte[] key = CommonUtil.base64Decode(CommonUtil.NVLL(response.header(Const.HEADER_ENCRYPTION_KEY)));
        final byte[] sign = CommonUtil.base64Decode(CommonUtil.NVLL(response.header(Const.HEADER_ENCRYPTION_SIGN)));
        try {
            if (!asymEncryptor.verify(key, sign)) {
                throw new LeonaRuntimeException(LeonaErrorCodeEnum.RSA_ENCRYPTION_VERIFY_FAIL);
            }
        } catch (InvalidKeyException | SignatureException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.RSA_ENCRYPTION_VERIFY_FAIL, e);
        }

        // AES秘钥解密
        final byte[] secretKey;
        try {
            secretKey = asymEncryptor.priDecode(key);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.RSA_PRIVATE_DECRYPTION_FAIL, e);
        }

        // 解密response body
        String content = response.body() == null ? "" : response.body().string();
        final byte[] decryptBody;
        try {
            decryptBody = symmEncryptor.decrypt(content, secretKey);
        } catch (InvalidKeyException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.DECRYPTION_FAIL, e);
        }

        return response.newBuilder()
                .body(ResponseBody.create(decryptBody, MEDIA_TYPE_JSON))
                .build();
    }

    // response header X-Lehui-Encryption-Level 不为 L1 则不解密
    private boolean isIgnore(Response response) {
        final String respEncryptLv = CommonUtil.NVLL(response.header(Const.HEADER_ENCRYPTION_LEVEL));
        return !respEncryptLv.equals(Const.HEADER_ENCRYPTION_LEVEL_L1);
    }
}
