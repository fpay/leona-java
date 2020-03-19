package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Withdrawal {
    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "request_id")
    private String requestID;

    @JSONField(name = "amount")
    private Integer amount;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "reason")
    private String reason;

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
