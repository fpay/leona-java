package com.lehuipay.leona.model;

import com.squareup.moshi.Json;

public class Withdrawal {
    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "request_id")
    private String requestID;

    @Json(name = "amount")
    private Integer amount;

    @Json(name = "status")
    private String status;

    @Json(name = "reason")
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
