package com.lehuipay.leona;

/**
 * 常量接口
 */
public interface Const {
    String HEADER_AGENTID           = "X-Lehui-AgentID";                    // 合作伙伴 ID
    String HEADER_NONCE             = "X-Lehui-Nonce";                      // 随机串
    String HEADER_SIGNATURE         = "X-Lehui-Signature";                  // 请求签名
    String HEADER_ENCRYPTION_LEVEL  = "X-Lehui-Encryption-Level";           // 数据加密等级
    String HEADER_ENCRYPTION_KEY    = "X-Lehui-Encryption-Key";             // 加密密钥
    String HEADER_ENCRYPTION_SIGN   = "X-Lehui-Encryption-Sign";            // 加密密钥签名
    String HEADER_ENCRYPTION_ACCEPT = "X-Lehui-Encryption-Accept";          // 响应体不加密

    String HEADER_ENCRYPTION_LEVEL_L0   = "L0";       // 响应体不加密标志
    String HEADER_ENCRYPTION_LEVEL_L1   = "L1";       // 临时密钥加密，由请求发起方生成临时密钥。
    String HEADER_ENCRYPTION_LEVEL_L2   = "L2";       // 持久密钥加密，合作伙伴和乐惠协商存储持久密钥。

    int NONCE_MIN_LENGTH    = 10;       // nonce最小长度
    int NONCE_MAX_LENGTH    = 16;       // nonce最大长度
    int SECRET_KEY_LENGTH   = 16;       // 临时密钥长度
    int IV_LENGTH           = 16;       // 随机向量长度

    // https://open.lehuipay.com
    String SERVER_HOST      = "https://open.hsh.lehuipay.com";
    String QRCODE_URL       = SERVER_HOST + "/api/v3/payments/qrcode";
    String MICROPAY_URL     = SERVER_HOST + "/api/v3/payments/micropay";
    String JSPAY_URL        = SERVER_HOST + "/api/v3/payments/jspay";
    String GET_ORDER_URL    = SERVER_HOST + "/api/v3/payments/query";
    String REFUND_URL       = SERVER_HOST + "/api/v3/refunds";
    String GET_REFUND_URL   = SERVER_HOST + "/api/v3/refunds/query";
}
