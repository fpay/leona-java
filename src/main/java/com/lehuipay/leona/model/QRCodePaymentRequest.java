package com.lehuipay.leona.model;

import com.lehuipay.leona.utils.CommonUtil;
import com.squareup.moshi.Json;

public class QRCodePaymentRequest {

    public QRCodePaymentRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePaymentRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.orderNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePaymentRequest, merchantID should not be empty");
        }
        if (builder.amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePaymentRequest, amount should be greater than zero");
        }
        this.merchantID = builder.merchantID;
        this.terminalID = builder.terminalID;
        this.orderNo = builder.orderNo;
        this.amount = builder.amount;
        this.notifyURL = builder.notifyURL;
        this.callbackURL = builder.callbackURL;
        this.tags = builder.tags;
    }

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "terminal_id")
    private String terminalID;

    @Json(name = "order_no")
    private String orderNo;

    @Json(name = "amount")
    private Integer amount;

    @Json(name = "notify_url")
    private String notifyURL;

    @Json(name = "callback_url")
    private String callbackURL;

    @Json(name = "tags")
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

        public QRCodePaymentRequest build(){
            return new QRCodePaymentRequest(this);
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
