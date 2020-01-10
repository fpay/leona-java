package com.lehuipay.leona;

import com.lehuipay.leona.contracts.Signer;
import com.lehuipay.leona.exception.LeonaErrorCodeEnum;
import com.lehuipay.leona.exception.LeonaRuntimeException;
import com.lehuipay.leona.utils.HMAC;
import com.lehuipay.leona.utils.CommonUtil;

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
     * @throws LeonaRuntimeException 签名异常
     */
    @Override
    public String sign(String body, String nonce) {
        StringBuilder sb = new StringBuilder();
        sb.append("agent_id=").append(agentID).append("&body=").append(body).append("&nonce=").append(nonce);

        final byte[] result;
        try {
            result = HMAC.hmacSHA256(agentKey.getBytes(), sb.toString().getBytes());
        } catch (LeonaRuntimeException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.SIGN_FAIL, e);
        }
        return HMAC.encode(result);
    }

    /**
     * http返回体验签
     *
     * @param body responseBody
     * @param nonce 随机串
     * @param signature 待验证的签名
     * @return 签名结果
     * @throws LeonaRuntimeException 验签异常
     */
    @Override
    public boolean verify(String body, String nonce, String signature) {
        final String signTarget;
        try {
            signTarget = sign(body, nonce);
        } catch (LeonaRuntimeException e) {    // 这里调用后了sign()方法, 需要使用e.getCause()得到真正的Exception
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.VERIFY_FAIL, e.getCause());
        }
        return signTarget.equals(signature);
    }
}
