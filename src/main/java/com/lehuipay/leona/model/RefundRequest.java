package com.lehuipay.leona.model;

import com.lehuipay.leona.utils.CommonUtil;
import com.squareup.moshi.Json;

public class RefundRequest {

    public RefundRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.RefundRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.orderNo) && CommonUtil.isEmpty(builder.transactionID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.RefundRequest, at least one of orderNo, transactionID");
        }
        if (CommonUtil.isEmpty(builder.refundNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.RefundRequest, refundNo should not be empty");
        }
        if (builder.amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.RefundRequest, amount should be greater than zero");
        }
        this.merchantID = builder.merchantID;
        this.orderNo = builder.orderNo;
        this.transactionID = builder.transactionID;
        this.refundNo = builder.refundNo;
        this.amount = builder.amount;
    }

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "order_no")
    private String orderNo;

    @Json(name = "transaction_id")
    private String transactionID;

    @Json(name = "refund_no")
    private String refundNo;

    @Json(name = "amount")
    private Integer amount;

    public String getMerchantID() {
        return merchantID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String merchantID;
        private String orderNo;
        private String transactionID;
        private String refundNo;
        private Integer amount;

        public Builder() {}

        public RefundRequest build(){
            return new RefundRequest(this);
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

        public Builder setRefundNo(String refundNo) {
            this.refundNo = refundNo;
            return this;
        }

        public Builder setAmount(Integer amount) {
            this.amount = amount;
            return this;
        }
    }
}
