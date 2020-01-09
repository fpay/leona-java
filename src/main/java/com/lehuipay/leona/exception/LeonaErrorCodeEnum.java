package com.lehuipay.leona.exception;

import com.lehuipay.leona.contracts.ErrorCode;

public enum LeonaErrorCodeEnum implements ErrorCode {

    /**
     * 未指明的异常
     */
    UNEXPECTED("client_error", "unexpected", "意料之外的错误"),

    // 合作伙伴API
    HTTP_ERROR("http_error", "http_error", "HTTP异常"),
    HTTP_CANCELLED("http_error", "http_cancelled", "HTTP取消"),

    SIGN_FAIL("client_error", "sign_fail", "签名失败"),
    VERIFY_FAIL("client_error", "verify_fail", "验签失败"),

    ENCRYPTION_FAIL("client_error", "encryption_fail", "加密失败"),
    DECRYPTION_FAIL("client_error", "decryption_fail", "解密失败"),

    RSA_PUBLIC_ENCRYPTION_FAIL("client_error", "rsa_public_encryption_fail", "RSA公钥加密失败"),
    RSA_PRIVATE_DECRYPTION_FAIL("client_error", "rsa_private_decryption_fail", "RSA私钥解密失败"),
    RSA_PRIVATE_ENCRYPTION_FAIL("client_error", "rsa_private_encryption_fail", "RSA私钥加密失败"),
    RSA_PUBLIC_DECRYPTION_FAIL("client_error", "rsa_public_decryption_fail", "RSA公钥解密失败"),


    RSA_ENCRYPTION_SIGN_FAIL("client_error", "rsa_encryption_sign_fail", "秘钥签名失败"),
    RSA_ENCRYPTION_VERIFY_FAIL("client_error", "rsa_encryption_verify_fail", "秘钥验签失败"),

    ;

    /**
     * 错误类型
     */
    private final String type;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 描述
     */
    private final String message;

    /**
     * @param code    错误码
     * @param message 描述
     */
    LeonaErrorCodeEnum(final String type, final String code, final String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
