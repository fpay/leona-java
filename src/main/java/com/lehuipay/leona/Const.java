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

    String SERVER_HOST              = "https://open.lehuipay.com";
    String QRCODE_PAY_URL           = SERVER_HOST + "/api/v3/payments/qrcode";
    String MICROPAY_URL             = SERVER_HOST + "/api/v3/payments/micropay";
    String JS_PAY_URL               = SERVER_HOST + "/api/v3/payments/jspay";
    String GET_ORDER_URL            = SERVER_HOST + "/api/v3/payments/query";
    String REFUND_URL               = SERVER_HOST + "/api/v3/refunds";
    String GET_REFUND_URL           = SERVER_HOST + "/api/v3/refunds/query";

    String GET_BILL_URL             = SERVER_HOST + "/api/v3/bills/download";
    String GET_BALANCE_URL          = SERVER_HOST + "/api/v3/wallets/query";
    String WITHDRAW_URL             = SERVER_HOST + "/api/v3/wallets/withdraw";
    String GET_WITHDRAW_DETAIL_URL  = SERVER_HOST + "/api/v3/wallets/query_withdrawal";

    String CLIENT_TYPE_WEIXIN   = "weixin";
    String CLIENT_TYPE_ALIPAY   = "alipay";
    String CLIENT_TYPE_UNIONPAY = "unionpay";
    String CLIENT_TYPE_JDPAY    = "jdpay";

    String PAYMENT_STATUS_PROCESSING = "processing";
    String PAYMENT_STATUS_SUCCEEDED  = "succeeded";
    String PAYMENT_STATUS_FAILED     = "failed";
    String PAYMENT_STATUS_CLOSED     = "closed";

    String REFUND_STATUS_CREATED    = "created";
    String REFUND_STATUS_PROCESSING = "processing";
    String REFUND_STATUS_SUCCEEDED  = "succeeded";
    String REFUND_STATUS_FAILED     = "failed";

    String WITHDRAWAL_STATUS_ACCEPTED   = "accepted";
    String WITHDRAWAL_STATUS_PROCESSING = "processing";
    String WITHDRAWAL_STATUS_SUCCEEDED  = "succeeded";
    String WITHDRAWAL_STATUS_FAILED     = "failed";
}
