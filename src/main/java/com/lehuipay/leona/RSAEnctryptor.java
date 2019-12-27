package com.lehuipay.leona;

import com.lehuipay.leona.contracts.AsymEncryptor;
import com.lehuipay.leona.utils.RSAEncrypt;
import com.lehuipay.leona.utils.Util;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAEnctryptor implements AsymEncryptor {

    public RSAEnctryptor(String partnerPriKey, String lhPubKey) {
        if (Util.isEmpty(partnerPriKey) || Util.isEmpty(lhPubKey)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.RSAEnctryptor, partnerPriKey and lhPubKey should not be empty");
        }
        this.privateKey = RSAEncrypt.getPrivateKey(partnerPriKey);
        this.publicKey = RSAEncrypt.getPublicKey(lhPubKey);
    }

    private RSAPrivateKey privateKey;

    private RSAPublicKey publicKey;

    @Override
    public byte[] pubEncode(String data) {
        return RSAEncrypt.publicEncrypt(publicKey, data);
    }

    @Override
    public byte[] priDecode(byte[] data) {
        return RSAEncrypt.privateDecrypt(privateKey, data);
    }

    @Override
    public byte[] sign(byte[] data) {
        return RSAEncrypt.sign(data, privateKey);
    }

    @Override
    public boolean verify(byte[] content, byte[] sign) {
        return RSAEncrypt.verify(content, sign, publicKey);
    }

}
