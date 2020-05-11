package com.lehuipay.leona;

import com.lehuipay.leona.utils.CommonUtil;

public class Options {

    public Options(String agentID, String agentKey, String agentPrivateKey, String lehuipayPublicKey, String encryptionLevel, String encryptionAccept, String secretKey) {
        if (CommonUtil.NVLL(encryptionLevel).equals(Const.HEADER_ENCRYPTION_LEVEL_L1) &&
                (CommonUtil.isEmpty(agentPrivateKey) || CommonUtil.isEmpty(lehuipayPublicKey))) {
            throw new IllegalArgumentException("partnerPriKey and lhPubKey should not be empty when encryptionLevel == 'L1'");
        }

        if (CommonUtil.NVLL(encryptionLevel).equals(Const.HEADER_ENCRYPTION_LEVEL_L2) &&
                CommonUtil.isEmpty(secretKey)) {
            throw new IllegalArgumentException("secretKey should not be empty when encryptionLevel == 'L2'");
        }

        if (CommonUtil.isEmpty(encryptionAccept) && !CommonUtil.isEmpty(encryptionLevel)) {
            encryptionAccept = encryptionLevel;
        }

        this.agentID = agentID;
        this.agentKey = agentKey;
        this.agentPrivateKey = agentPrivateKey;
        this.lehuipayPublicKey = lehuipayPublicKey;
        this.encryptionLevel = encryptionLevel;
        this.encryptionAccept = encryptionAccept;
        this.secretKey = secretKey;
    }

    private final String agentID;                 // 合作伙伴ID

    private final String agentKey;                // 合作伙伴密钥，用于数据签名

    private final String agentPrivateKey;           // 合作伙伴私钥

    private final String lehuipayPublicKey;                // 乐惠公钥

    private final String encryptionLevel;         // 加密等级

    private final String encryptionAccept;        // 响应体不加密

    private final String secretKey;               // L2加密, 持久秘钥

    public String getAgentID() {
        return agentID;
    }

    public String getAgentKey() {
        return agentKey;
    }

    public String getAgentPrivateKey() {
        return agentPrivateKey;
    }

    public String getLehuipayPublicKey() {
        return lehuipayPublicKey;
    }

    public String getEncryptionLevel() {
        return encryptionLevel;
    }

    public String getEncryptionAccept() {
        return encryptionAccept;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
