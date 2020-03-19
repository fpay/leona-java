package com.lehuipay.leona;

import com.lehuipay.leona.contracts.Client;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.model.MicropayPaymentResponse;
import com.lehuipay.leona.model.QueryBalanceRequest;
import com.lehuipay.leona.model.QueryBalanceResponse;
import com.lehuipay.leona.model.DownloadBillsRequest;
import com.lehuipay.leona.model.QueryPaymentRequest;
import com.lehuipay.leona.model.QueryPaymentResponse;
import com.lehuipay.leona.model.QueryRefundRequest;
import com.lehuipay.leona.model.QueryRefundResponse;
import com.lehuipay.leona.model.QueryWithdrawalRequest;
import com.lehuipay.leona.model.JspayPaymentRequest;
import com.lehuipay.leona.model.JspayPaymentResponse;
import com.lehuipay.leona.model.MicropayPaymentRequest;
import com.lehuipay.leona.model.QRCodePaymentRequest;
import com.lehuipay.leona.model.QRCodePaymentResponse;
import com.lehuipay.leona.model.RefundRequest;
import com.lehuipay.leona.model.RefundResponse;
import com.lehuipay.leona.model.WithdrawRequest;
import com.lehuipay.leona.model.QueryWithdrawalResponse;
import com.lehuipay.leona.model.WithdrawResponse;
import com.lehuipay.leona.utils.CommonUtil;

import java.io.IOException;
import java.io.OutputStream;

/**
 * LeonaClient一个线程安全的, 支持异步的, 可复用的客户端工具类.
 * 多个请求请使用同一个LeonaClient实例, 避免重复初始化.
 *
 * <code>
 *         Client client = LeonaClient.builder()
 *                 .setAgentID(agent_id)
 *                 .setAgentKey(agent_key)
 * //                .setPartnerPriKey(cliPriKeyFilePath)
 * //                .setLhPubKey(serPubKeyFilePath)
 * //                .setSecretKey(secret_key)
 * //                .setEncryptionLevel(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L1)
 * //                .setEncryptionAccept(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L0)
 *                 .build();
 *
 *         // 异步
 *         try {
 *             final QRCodePaymentRequest request = QRCodePaymentRequest.builder()
 *                     .setMerchantID(merchantID)
 *                     .setTerminalID("2")
 *                     .setOrderNo("20200313000000000001")
 *                     .setAmount(1)
 *                     .setTags(new String[]{"tag1", "tag2"})
 *                     .build();
 *             client.createQRCodePayment(request, (e, data) -> {
 *                 if (e != null) {
 *                     System.err.println(e);
 *                     return;
 *                 }
 *                 System.out.println(data);
 *             });
 *         } catch (LeonaException e) {
 *             System.err.println(e);
 *         }
 *
 *         // 同步
 *         try {
 *             final QRCodePayRequest request = QRCodePayRequest.builder()
 *  *                     .setMerchantID(merchantID)
 *  *                     .setTerminalID("2")
 *  *                     .setOrderNo("20200313000000000001")
 *  *                     .setAmount(1)
 *  *                     .setTags(new String[]{"tag1", "tag2"})
 *  *                     .build();
 *             final QRCodePaymentResponse response = client.createQRCodePayment(request);
 *             System.out.println(response);
 *         } catch (LeonaException e) {
 *             System.err.printf(e);
 *         }
 * </code>
 *
 *
 *
 * <p>该类所有同步的http请求都会抛出{@link LeonaException}. 具体情况为--加签验签, 加密解密, 网络IO异常, httpRequest返回状态码不为200, 统一抛出{@link LeonaException},
 * 区别在于{@link LeonaException}的{@code getType}, {@code getCode}, {@code getMessage}方法各自拥有不同的结果.
 * 异常结果详见枚举类 {@link com.lehuipay.leona.exception.LeonaErrorCodeEnum}.
 * httpRequest返回状态码不为200返回的{@code LeonaException}是responseBody的封装, 具体内容见
 * <a href="http://open.hsh.lehuipay.com/docs/">合作伙伴平台API手册</a>
 *
 * <p>而该类所有异步的http请求也会抛出{@link LeonaException}. 情况为--加签验签, 加密解密, 网络IO异常.
 * httpRequest返回状态码不为200的异常情况不在其中, 而在方法的callback参数中进行处理.
 * @see com.lehuipay.leona.Callback
 * httpRequest返回状态码不为200情况下返回的{@code LeonaException}是responseBody的封装, 具体内容见
 * <a href="http://open.hsh.lehuipay.com/docs/">合作伙伴平台API手册</a>
 *
 */
public class LeonaClient implements Client {

    private final HttpClient httpClient;

    private LeonaClient(Builder builder) {
        this.options = new Options(builder.agentID,
                builder.agentKey,
                builder.partnerPriKey,
                builder.lhPubKey,
                builder.encryptionLevel,
                builder.encryptionAccept,
                builder.secretKey);

        httpClient = new HttpClient(this.options);
    }

    private final Options options;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String agentID;
        private String agentKey;
        private String partnerPriKey;
        private String lhPubKey;
        private String encryptionLevel;
        private String encryptionAccept;
        private String secretKey;

        public Builder() {}

        public Builder setAgentID(String agentID) {
            if (CommonUtil.isEmpty(agentID)) {
                throw new IllegalArgumentException("agentID should not be empty");
            }
            this.agentID = agentID;
            return this;
        }

        public Builder setAgentKey(String agentKey) {
            if (CommonUtil.isEmpty(agentKey)) {
                throw new IllegalArgumentException("agentKey should not be empty");
            }
            this.agentKey = agentKey;
            return this;
        }

        /**
         * 加载私钥
         *
         * @param partnerPriKeyFilePath 私钥文件路径
         * @return LeonaClient构造器
         * @throws IOException 读取文件产生的IO异常
         */
        public Builder setPartnerPriKey(String partnerPriKeyFilePath) throws IOException {
            if (CommonUtil.isEmpty(partnerPriKeyFilePath)) {
                throw new IllegalArgumentException("partnerPriKeyFilePath should not be empty");
            }
            this.partnerPriKey = CommonUtil.readPemFile2String(partnerPriKeyFilePath);
            return this;
        }

        /**
         * 加载公钥
         *
         * @param lhPubKeyFilePath 公钥文件路径
         * @return LeonaClient构造器
         * @throws IOException 读取文件产生的IO异常
         */
        public Builder setLhPubKey(String lhPubKeyFilePath) throws IOException {
            if (CommonUtil.isEmpty(lhPubKeyFilePath)) {
                throw new IllegalArgumentException("lhPubKeyFilePath should not be empty");
            }
            this.lhPubKey = CommonUtil.readPemFile2String(lhPubKeyFilePath);
            return this;
        }

        public Builder setEncryptionLevel(String encryptionLevel) {
            if (CommonUtil.isEmpty(encryptionLevel)) {
                throw new IllegalArgumentException("encryptionLevel should not be empty");
            }
            this.encryptionLevel = encryptionLevel;
            return this;
        }

        public Builder setEncryptionAccept(String encryptionAccept) {
            if (CommonUtil.isEmpty(encryptionAccept)) {
                throw new IllegalArgumentException("encryptionAccept should not be empty");
            }
            this.encryptionAccept = encryptionAccept;
            return this;
        }

        public Builder setSecretKey(String secretKey) {
            if (CommonUtil.isEmpty(secretKey)) {
                throw new IllegalArgumentException("secretKey should not be empty");
            }
            this.secretKey = secretKey;
            return this;
        }

        public LeonaClient build() {
            return new LeonaClient(this);
        }
    }

    /**
     * 二维码支付
     *
     * @param req 请求体
     * @return 二维码支付结果
     * @throws LeonaException 说明见类文档
     *
     */
    public QRCodePaymentResponse createQRCodePayment(QRCodePaymentRequest req) throws LeonaException {
        return httpClient.request("POST", Const.QRCODE_PAYMENT_URL, req, QRCodePaymentResponse.class);
    }

    /**
     * 二维码支付(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void createQRCodePayment(QRCodePaymentRequest req, Callback<QRCodePaymentResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.QRCODE_PAYMENT_URL, req, QRCodePaymentResponse.class, callback);

    }

    /**
     * 刷卡交易
     *
     * @param req 请求体
     * @return 刷卡交易结果
     * @throws LeonaException 说明见类文档
     */
    public MicropayPaymentResponse createMicropayPayment(MicropayPaymentRequest req) throws LeonaException {
        return httpClient.request("POST", Const.MICROPAY_PAYMENT_URL, req, MicropayPaymentResponse.class);
    }

    /**
     * 刷卡交易(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void createMicropayPayment(MicropayPaymentRequest req, Callback<MicropayPaymentResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.MICROPAY_PAYMENT_URL, req, MicropayPaymentResponse.class, callback);
    }

    /**
     * 查询交易
     *
     * @param req 请求体
     * @return 查询交易结果
     * @throws LeonaException 说明见类文档
     */
    public QueryPaymentResponse queryPayment(QueryPaymentRequest req) throws LeonaException {
        return httpClient.request("POST", Const.QUERY_PAYMENT_URL, req, QueryPaymentResponse.class);
    }

    /**
     * 查询交易(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void queryPayment(QueryPaymentRequest req, Callback<QueryPaymentResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.QUERY_PAYMENT_URL, req, QueryPaymentResponse.class, callback);
    }

    /**
     * 退款
     *
     * @param req 请求体
     * @return 退款结果
     * @throws LeonaException 说明见类文档
     */
    public RefundResponse createRefund(RefundRequest req) throws LeonaException {
        return httpClient.request("POST", Const.REFUND_URL, req, RefundResponse.class);
    }

    /**
     * 退款(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void createRefund(RefundRequest req, Callback<RefundResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.REFUND_URL, req, RefundResponse.class, callback);
    }

    /**
     * 查询退款
     *
     * @param req 请求体
     * @return 查询退款结果
     * @throws LeonaException 说明见类文档
     */
    public QueryRefundResponse queryRefund(QueryRefundRequest req) throws LeonaException {
        return httpClient.request("POST", Const.QUERY_REFUND_URL, req, QueryRefundResponse.class);
    }

    /**
     * 查询退款(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void queryRefund(QueryRefundRequest req, Callback<QueryRefundResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.QUERY_REFUND_URL, req, QueryRefundResponse.class, callback);
    }

    /**
     * 查询账户余额
     *
     * @param req 请求体
     * @return 查询余额结果
     * @throws LeonaException 说明见类文档
     */
    @Override
    public QueryBalanceResponse queryBalance(QueryBalanceRequest req) throws LeonaException {
        return httpClient.request("POST", Const.QUERY_BALANCE_URL, req, QueryBalanceResponse.class);
    }

    /**
     * 查询账户余额(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void queryBalance(QueryBalanceRequest req, Callback<QueryBalanceResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.QUERY_BALANCE_URL, req, QueryBalanceResponse.class, callback);
    }

    /**
     * 手动提现
     *
     * @param req 请求体
     * @return 手动提现结果
     * @throws LeonaException 说明见类文档
     */
    @Override
    public WithdrawResponse withdraw(WithdrawRequest req) throws LeonaException {
        return httpClient.request("POST", Const.WITHDRAW_URL, req, WithdrawResponse.class);
    }

    /**
     * 手动提现(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void withdraw(WithdrawRequest req, Callback<WithdrawResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.WITHDRAW_URL, req, WithdrawResponse.class, callback);
    }

    /**
     * 查询提现结果
     *
     * @param req 请求体
     * @return 提现结果
     * @throws LeonaException 说明见类文档
     */
    @Override
    public QueryWithdrawalResponse queryWithdrawal(QueryWithdrawalRequest req) throws LeonaException {
        return httpClient.request("POST", Const.QUERY_WITHDRAWAL_URL, req, QueryWithdrawalResponse.class);
    }

    /**
     * 查询提现结果(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void queryWithdrawal(QueryWithdrawalRequest req, Callback<QueryWithdrawalResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.QUERY_WITHDRAWAL_URL, req, QueryWithdrawalResponse.class, callback);
    }

    /**
     * 获取账单
     *
     * @param req 请求体
     * @return 提现结果
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void downloadBills(DownloadBillsRequest req, OutputStream dst) throws LeonaException {
        httpClient.download("POST", Const.DOWNLOAD_BILLS_URL, req, dst);
    }

    /**
     * 获取账单(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void downloadBills(DownloadBillsRequest req, Callback<OutputStream> callback) throws LeonaException {
        httpClient.download("POST", Const.DOWNLOAD_BILLS_URL, req, callback);
    }

    /**
     * 消费者扫商家二维码支付
     *
     * @param req 请求体
     * @return 提现结果
     * @throws LeonaException 说明见类文档
     */
    @Override
    public JspayPaymentResponse createJspayPayment(JspayPaymentRequest req) throws LeonaException {
        return httpClient.request("POST", Const.JSPAY_PAYMENT_URL, req, JspayPaymentResponse.class);
    }

    /**
     * 消费者扫商家二维码支付(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void createJspayPayment(JspayPaymentRequest req, Callback<JspayPaymentResponse> callback) throws LeonaException {
        httpClient.request("POST", Const.JSPAY_PAYMENT_URL, req, JspayPaymentResponse.class, callback);
    }


    // ******************setter & getter********************

    /**
     * 获得LeonaClient的初始化参数
     *
     * @return LeonaClient属性参数
     */
    public Options getOptions() {
        return options;
    }
}
