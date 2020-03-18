package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class QRCodePayRequest {

    public QRCodePayRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePayRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.orderNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePayRequest, merchantID should not be empty");
        }
        if (builder.amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePayRequest, amount should be greater than zero");
        }
        this.merchantID = builder.merchantID;
        this.terminalID = builder.terminalID;
        this.orderNo = builder.orderNo;
        this.amount = builder.amount;
        this.notifyURL = builder.notifyURL;
        this.callbackURL = builder.callbackURL;
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

    @JSONField(name = "notify_url")
    private String notifyURL;

    @JSONField(name = "callback_url")
    private String callbackURL;

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

    public String getNotifyURL() {
        return notifyURL;
    }

    public String getCallbackURL() {
        return callbackURL;
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
        private String notifyURL;
        private String callbackURL;
        private String[] tags;

        public Builder() {}

        public QRCodePayRequest build(){
            return new QRCodePayRequest(this);
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

        public Builder setNotifyURL(String notifyURL) {
            this.notifyURL = notifyURL;
            return this;
        }

        public Builder setCallbackURL(String callbackURL) {
            this.callbackURL = callbackURL;
            return this;
        }

        public Builder setTags(String[] tags) {
            this.tags = tags;
            return this;
        }
    }
}
