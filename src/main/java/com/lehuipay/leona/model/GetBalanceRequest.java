package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class GetBalanceRequest {

    public GetBalanceRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBalanceRequest, merchantID should not be empty");
        }
        this.merchantID = builder.merchantID;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    public String getMerchantID() {
        return merchantID;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String merchantID;

        public Builder() {}

        public GetBalanceRequest build(){
            return new GetBalanceRequest(this);
        }

        public Builder setMerchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }
    }
}