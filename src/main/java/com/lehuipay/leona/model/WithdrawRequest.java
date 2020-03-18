package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class WithdrawRequest {

    public WithdrawRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.WithdrawRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.requestID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.WithdrawRequest, requestID should not be empty");
        }
        if (builder.amount < 1000) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.WithdrawRequest, amount should be greater than 1000");
        }

        this.merchantID = builder.merchantID;
        this.requestID = builder.requestID;
        this.amount = builder.amount;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "request_id")
    private String requestID;

    @JSONField(name = "amount")
    private Integer amount;

    public String getMerchantID() {
        return merchantID;
    }

    public String getRequestID() {
        return requestID;
    }

    public Integer getAmount() {
        return amount;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String merchantID;
        private String requestID;
        private Integer amount;

        public Builder() {}

        public WithdrawRequest build(){
            return new WithdrawRequest(this);
        }

        public Builder setMerchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }

        public Builder setRequestID(String requestID) {
            this.requestID = requestID;
            return this;
        }

        public Builder setAmount(Integer amount) {
            this.amount = amount;
            return this;
        }
    }
}
