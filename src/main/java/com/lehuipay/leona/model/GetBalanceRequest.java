package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class GetBalanceRequest {

    public GetBalanceRequest(String merchantID) {
        if (CommonUtil.isEmpty(merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBalanceRequest, merchantID should not be empty");
        }
        this.merchantID = merchantID;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    public String getMerchantID() {
        return merchantID;
    }
}