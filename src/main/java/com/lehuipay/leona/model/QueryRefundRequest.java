package com.lehuipay.leona.model;

import com.lehuipay.leona.utils.CommonUtil;
import com.squareup.moshi.Json;

public class QueryRefundRequest {


    public QueryRefundRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QueryRefundRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.orderNo) && CommonUtil.isEmpty(builder.transactionID) &&
                CommonUtil.isEmpty(builder.refundNo) && CommonUtil.isEmpty(builder.refundID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QueryRefundRequest, at least one of orderNo, transactionID, refundNo, refundID");
        }
        this.merchantID = builder.merchantID;
        this.orderNo = builder.orderNo;
        this.transactionID = builder.transactionID;
        this.refundNo = builder.refundNo;
        this.refundID = builder.refundID;
    }

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "order_no")
    private String orderNo;

    @Json(name = "transaction_id")
    private String transactionID;

    @Json(name = "refund_no")
    private String refundNo;

    @Json(name = "refund_id")
    private String refundID;

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

    public String getRefundID() {
        return refundID;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String merchantID;
        private String orderNo;
        private String transactionID;
        private String refundNo;
        private String refundID;

        public Builder() {}

        public QueryRefundRequest build(){
            return new QueryRefundRequest(this);
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

        public Builder setRefundID(String refundID) {
            this.refundID = refundID;
            return this;
        }
    }
}
