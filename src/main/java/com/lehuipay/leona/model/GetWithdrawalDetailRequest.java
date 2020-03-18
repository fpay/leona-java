package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class GetWithdrawalDetailRequest {

    public GetWithdrawalDetailRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetWithdrawalDetailRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(builder.requestID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetWithdrawalDetailRequest, requestID should not be empty");
        }
        this.merchantID = builder.merchantID;
        this.requestID = builder.requestID;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "request_id")
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

        public GetWithdrawalDetailRequest build(){
            return new GetWithdrawalDetailRequest(this);
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
