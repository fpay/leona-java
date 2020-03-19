package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class MicropayPaymentRequest {

    public MicropayPaymentRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicropayPaymentRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.terminalID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicropayPaymentRequest, terminalID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.orderNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicropayPaymentRequest, orderNo should not be empty");
        }
        if (builder.amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicropayPaymentRequest, amount should be greater than zero");
        }
        if (CommonUtil.isEmpty(builder.authCode)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicropayPaymentRequest, authCode should not be empty");
        }

        this.merchantID = builder.merchantID;
        this.terminalID = builder.terminalID;
        this.orderNo = builder.orderNo;
        this.amount = builder.amount;
        this.authCode = builder.authCode;
        this.notifyURL = builder.notifyURL;
        this.clientIP = builder.clientIP;
        this.tags = builder.tags;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "terminal_id")
    private String terminalID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "amount")
    private Integer amount;

    @JSONField(name = "auth_code")
    private String authCode;

    @JSONField(name = "notify_url")
    private String notifyURL;

    @JSONField(name = "client_ip")
    private String clientIP;

    @JSONField(name = "tags")
    private String[] tags;

    public String getMerchantID() {
        return merchantID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String[] getTags() {
        return tags;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String merchantID;
        private String terminalID;
        private String orderNo;
        private Integer amount;
        private String authCode;
        private String notifyURL;
        private String clientIP;
        private String[] tags;

        public Builder() {}

        public MicropayPaymentRequest build(){
            return new MicropayPaymentRequest(this);
        }

        public Builder setMerchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }

        public Builder setTerminalID(String terminalID) {
            this.terminalID = terminalID;
            return this;
        }

        public Builder setOrderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }

        public Builder setAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public Builder setAuthCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder setNotifyURL(String notifyURL) {
            this.notifyURL = notifyURL;
            return this;
        }

        public Builder setClientIP(String clientIP) {
            this.clientIP = clientIP;
            return this;
        }

        public Builder setTags(String[] tags) {
            this.tags = tags;
            return this;
        }
    }
}
