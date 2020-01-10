package com.lehuipay.leona;

import com.lehuipay.leona.contracts.Leona;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.exception.LeonaRuntimeException;
import com.lehuipay.leona.model.GetOrderRequest;
import com.lehuipay.leona.model.GetRefundRequest;
import com.lehuipay.leona.model.MicroPayRequest;
import com.lehuipay.leona.model.Payment;
import com.lehuipay.leona.model.QRCodePayRequest;
import com.lehuipay.leona.model.QRCodePayResponse;
import com.lehuipay.leona.model.Refund;
import com.lehuipay.leona.model.RefundRequest;
import com.lehuipay.leona.utils.CommonUtil;

import java.io.IOException;

/**
 * LeonaClient一个线程安全的, 支持异步的, 可复用的客户端工具类.
 * 多个请求请使用同一个LeonaClient实例, 避免重复初始化.
 *
 * <code>
 *         Leona client = new LeonaClient
 *                 .Builder(agent_id, agent_key)
 * //                .setPartnerPriKey(cliPriKeyFilePath)
 * //                .setLhPubKey(serPubKeyFilePath)
 * //                .setSecretKey(secret_key)
 * //                .setEncryptionLevel(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L1)
 * //                .setEncryptionAccept(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L0)
 *                 .build();
 *
 *         // 异步
 *         try {
 *             final QRCodePayRequest req =
 *                     new QRCodePayRequest(merchantID, "2", "xxxxxxx", 1, null, null);
 *             client.qrCodePay(req, (e, data) -> {
 *                 if (e != null) {
 *                     String type = e.getType();
 *                     String code = e.getCode();
 *                     String message = e.getMessage();
 *                     System.err.printf("type: %s, code: %s, message: %s\n", type, code, message);
 *                     return;
 *                 }
 *                 System.out.println(data);
 *             });
 *         } catch (LeonaException e) {
 *             String type = e.getType();
 *             String code = e.getCode();
 *             String message = e.getMessage();
 *             System.err.printf("type: %s, code: %s, message: %s\n", type, code, message);
 * //            e.printStackTrace();
 *         }
 *         // 同步
 *         try {
 *             final QRCodePayRequest req =
 *                     new QRCodePayRequest(merchantID, "2", "xxxxxxx", 1, null, null);
 *             final QRCodePayResponse resp = client.qrCodePay(req);
 *             System.out.println(resp);
 *         } catch (LeonaException e) {
 *             String type = e.getType();
 *             String code = e.getCode();
 *             String message = e.getMessage();
 *             System.err.printf("type: %s, code: %s, message: %s\n", type, code, message);
 * //            e.printStackTrace();
 *         }
 * </code>
 *
 *
 *
 * <p>该类所有同步的http请求都会抛出{@link LeonaException}. 具体情况为--加签验签, 加密解密, 网络IO异常, httpRequest返回状态码不为200, 统一抛出{@link LeonaException},
 * 区别在于{@link LeonaException#getType()}, {@link LeonaException#getCode()}, {@link LeonaException#getMessage()}各自拥有不同的结果.
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
public class LeonaClient implements Leona {

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

    public static class Builder {
        private final String agentID;
        private final String agentKey;
        private String partnerPriKey;
        private String lhPubKey;
        private String encryptionLevel;
        private String encryptionAccept;
        private String secretKey;

        public Builder(String agentID, String agentKey) {
            if (CommonUtil.isEmpty(agentID)) {
                throw new IllegalArgumentException("agentID should not be empty");
            }
            if (CommonUtil.isEmpty(agentKey)) {
                throw new IllegalArgumentException("agentKey should not be empty");
            }
            this.agentID = agentID;
            this.agentKey = agentKey;
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
    public QRCodePayResponse qrCodePay(QRCodePayRequest req) throws LeonaException {
        try {
            return httpClient.request("POST", Const.QRCODE_URL, req, QRCodePayResponse.class);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        } catch (IOException e1) {
            throw new LeonaException(new LeonaRuntimeException(e1));
        }
    }

    /**
     * 二维码支付(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void qrCodePay(QRCodePayRequest req, Callback<QRCodePayResponse> callback) throws LeonaException {
        try {
            httpClient.request("POST", Const.QRCODE_URL, req, QRCodePayResponse.class, callback);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        }

    }

    /**
     * 刷卡交易
     *
     * @param req 请求体
     * @return 刷卡交易结果
     * @throws LeonaException 说明见类文档
     */
    public Payment microPay(MicroPayRequest req) throws LeonaException {
        try {
            return httpClient.request("POST", Const.MICROPAY_URL, req, Payment.class);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        } catch (IOException e1) {
            throw new LeonaException(new LeonaRuntimeException(e1));
        }
    }

    /**
     * 刷卡交易(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void microPay(MicroPayRequest req, Callback<Payment> callback) throws LeonaException {
        try {
            httpClient.request("POST", Const.MICROPAY_URL, req, Payment.class, callback);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        }
    }

    /**
     * 查询交易
     *
     * @param req 请求体
     * @return 查询交易结果
     * @throws LeonaException 说明见类文档
     */
    public Payment getOrder(GetOrderRequest req) throws LeonaException {
        try {
            return httpClient.request("POST", Const.GET_ORDER_URL, req, Payment.class);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        } catch (IOException e1) {
            throw new LeonaException(new LeonaRuntimeException(e1));
        }
    }

    /**
     * 查询交易(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void getOrder(GetOrderRequest req, Callback<Payment> callback) throws LeonaException {
        try {
            httpClient.request("POST", Const.GET_ORDER_URL, req, Payment.class, callback);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        }

    }

    /**
     * 退款
     *
     * @param req 请求体
     * @return 退款结果
     * @throws LeonaException 说明见类文档
     */
    public Refund refund(RefundRequest req) throws LeonaException {
        try {
            return httpClient.request("POST", Const.REFUND_URL, req, Refund.class);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        } catch (IOException e1) {
            throw new LeonaException(new LeonaRuntimeException(e1));
        }
    }

    /**
     * 退款(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void refund(RefundRequest req, Callback<Refund> callback) throws LeonaException {
        try {
            httpClient.request("POST", Const.REFUND_URL, req, Refund.class, callback);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        }
    }

    /**
     * 查询退款
     *
     * @param req 请求体
     * @return 查询退款结果
     * @throws LeonaException 说明见类文档
     */
    public Refund getRefund(GetRefundRequest req) throws LeonaException {
        try {
            return httpClient.request("POST", Const.GET_REFUND_URL, req, Refund.class);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        } catch (IOException e1) {
            throw new LeonaException(new LeonaRuntimeException(e1));
        }
    }

    /**
     * 查询退款(异步)
     *
     * @param req 请求体
     * @param callback {@link com.lehuipay.leona.Callback}, 定义了请求失败与成功时应有的操作, 详见接口说明
     * @throws LeonaException 说明见类文档
     */
    @Override
    public void getRefund(GetRefundRequest req, Callback<Refund> callback) throws LeonaException {
        try {
            httpClient.request("POST", Const.GET_REFUND_URL, req, Refund.class, callback);
        } catch (LeonaRuntimeException e) {
            throw new LeonaException(e);
        }
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
