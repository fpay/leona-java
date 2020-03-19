package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class QueryBalanceRequest {

    public QueryBalanceRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QueryBalanceRequest, merchantID should not be empty");
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

        public QueryBalanceRequest build(){
            return new QueryBalanceRequest(this);
        }

        public Builder setMerchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }
    }
}