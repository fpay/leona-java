package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class QueryPaymentRequest {

    public QueryPaymentRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QueryPaymentRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.orderNo) && CommonUtil.isEmpty(builder.transactionID)) {
            throw new NumberFormatException("init com.lehuipay.leona.model.QueryPaymentRequest, at least one of orderNo and transactionID");
        }
        this.merchantID = builder.merchantID;
        this.orderNo = builder.orderNo;
        this.transactionID = builder.transactionID;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "transaction_id")
    private String transactionID;

    public String getMerchantID() {
        return merchantID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder {
        private String merchantID;
        private String orderNo;
        private String transactionID;

        public Builder() {}

        public QueryPaymentRequest build(){
            return new QueryPaymentRequest(this);
        }

        public Builder setMerchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }

        public Builder setOrderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }

        public Builder setTransactionID(String transactionID) {
            this.transactionID = transactionID;
            return this;
        }
    }
}
