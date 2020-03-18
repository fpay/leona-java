package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

import java.util.HashSet;

public class JSPayRequest {

    private HashSet clientTypeSet =  new HashSet() {
        {
            add("weixin");
            add("alipay");
            add("unionpay");
            add("jdpay");
        }
    };

    public JSPayRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.orderNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, orderNo should not be empty");
        }
        if (builder.amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, amount should be greater than zero");
        }
        if (CommonUtil.isEmpty(builder.clientType)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, clientType should not be empty");
        }
        if (!clientTypeSet.contains(builder.clientType)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, clientType should be one of [weixin, alipay, unionpay, jdpay]");
        }
        if (CommonUtil.isEmpty(builder.appID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, appID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.buyerID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, buyerID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.clientIP)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, clientIP should not be empty");
        }

        this.merchantID = builder.merchantID;
        this.terminalID = builder.terminalID;
        this.orderNo = builder.orderNo;
        this.amount = builder.amount;
        this.clientType = builder.clientType;
        this.appID = builder.appID;
        this.buyerID = builder.buyerID;
        this.clientIP = builder.clientIP;
        this.notifyURL = builder.notifyURL;
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

    @JSONField(name = "client_type")
    private String clientType;

    @JSONField(name = "app_id")
    private String appID;

    @JSONField(name = "buyer_id")
    private String buyerID;

    @JSONField(name = "client_ip")
    private String clientIP;

    @JSONField(name = "notify_url")
    private String notifyURL;

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

    public String getClientType() {
        return clientType;
    }

    public String getAppID() {
        return appID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getNotifyURL() {
        return notifyURL;
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
        private String clientType;
        private String appID;
        private String buyerID;
        private String clientIP;
        private String notifyURL;
        private String[] tags;

        public Builder() {}

        public JSPayRequest build(){
            return new JSPayRequest(this);
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

        public Builder setClientType(String clientType) {
            this.clientType = clientType;
            return this;
        }

        public Builder setAppID(String appID) {
            this.appID = appID;
            return this;
        }

        public Builder setBuyerID(String buyerID) {
            this.buyerID = buyerID;
            return this;
        }

        public Builder setClientIP(String clientIP) {
            this.clientIP = clientIP;
            return this;
        }

        public Builder setNotifyURL(String notifyURL) {
            this.notifyURL = notifyURL;
            return this;
        }

        public Builder setTags(String[] tags) {
            this.tags = tags;
            return this;
        }
    }
}
