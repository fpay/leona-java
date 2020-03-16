package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class WithdrawRequest {

    public WithdrawRequest(String merchantID, String requestID, Integer amount) {
        if (CommonUtil.isEmpty(merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.WithdrawRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(requestID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.WithdrawRequest, requestID should not be empty");
        }
        if (amount < 1000) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.WithdrawRequest, amount should be greater than 1000");
        }

        this.merchantID = merchantID;
        this.requestID = requestID;
        this.amount = amount;
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
}
