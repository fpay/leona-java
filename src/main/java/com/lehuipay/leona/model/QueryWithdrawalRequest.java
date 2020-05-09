package com.lehuipay.leona.model;

import com.lehuipay.leona.utils.CommonUtil;
import com.squareup.moshi.Json;

public class QueryWithdrawalRequest {

    public QueryWithdrawalRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QueryWithdrawalRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.requestID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QueryWithdrawalRequest, requestID should not be empty");
        }
        this.merchantID = builder.merchantID;
        this.requestID = builder.requestID;
    }

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "request_id")
    private String requestID;

    public String getMerchantID() {
        return merchantID;
    }

    public String getRequestID() {
        return requestID;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String merchantID;
        private String requestID;

        public Builder() {}

        public QueryWithdrawalRequest build(){
            return new QueryWithdrawalRequest(this);
        }

        public Builder setMerchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }

        public Builder setRequestID(String requestID) {
            this.requestID = requestID;
            return this;
        }
    }
}
