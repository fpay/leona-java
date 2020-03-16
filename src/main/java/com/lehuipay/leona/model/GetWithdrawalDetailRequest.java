package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class GetWithdrawalDetailRequest {

    public GetWithdrawalDetailRequest(String merchantID, String requestID) {
        if (CommonUtil.isEmpty(merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetWithdrawalDetailRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(requestID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetWithdrawalDetailRequest, requestID should not be empty");
        }
        this.merchantID = merchantID;
        this.requestID = requestID;
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
}
