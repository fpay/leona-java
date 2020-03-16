package com.lehuipay.leona;

import com.lehuipay.leona.contracts.Signer;
import com.lehuipay.leona.utils.HMAC;
import com.lehuipay.leona.utils.CommonUtil;

import java.security.InvalidKeyException;

/**
 * HMAC-SHA256签名器
 */
public class HMACSigner implements Signer {

    public HMACSigner(String agentID, String agentKey) {
        if (CommonUtil.isEmpty(agentID) || CommonUtil.isEmpty(agentKey)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.HMCASigner, agentID and agentKey should not be empty");
        }
        this.agentID = agentID;
        this.agentKey = agentKey;
    }

    private String agentID;

    private String agentKey;

    /**
     * http请求体加签
     *
     * @param body requestBody
     * @param nonce 随机串
     * @return 签名
     * @throws InvalidKeyException
     */
    @Override
    public String sign(String body, String nonce) throws InvalidKeyException {
        StringBuilder sb = new StringBuilder();
        sb.append("agent_id=").append(agentID).append("&body=").append(body).append("&nonce=").append(nonce);

        final byte[] result = HMAC.hmacSHA256(agentKey.getBytes(), sb.toString().getBytes());;
        return HMAC.encode(result);
    }

    /**
     * http返回体验签
     *
     * @param body responseBody
     * @param nonce 随机串
     * @param signature 待验证的签名
     * @return 签名结果
     * @throws InvalidKeyException
     */
    @Override
    public boolean verify(String body, String nonce, String signature) throws InvalidKeyException {
        final String signTarget = sign(body, nonce);
        return signTarget.equals(signature);
    }
}
