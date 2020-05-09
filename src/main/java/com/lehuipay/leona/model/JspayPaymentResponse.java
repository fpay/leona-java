package com.lehuipay.leona.model;

import com.squareup.moshi.Json;

public class JspayPaymentResponse {

    @Json(name = "transaction_id")
    private String transactionID;

    @Json(name = "order_no")
    private String orderNo;

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "prepay_id")
    private String prepayID;

    @Json(name = "js_data")
    private String jsData;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getPrepayID() {
        return prepayID;
    }

    public void setPrepayID(String prepayID) {
        this.prepayID = prepayID;
    }

    public String getJsData() {
        return jsData;
    }

    public void setJsData(String jsData) {
        this.jsData = jsData;
    }

    @Override
    public String toString() {
        return "JSPayResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", merchantID='" + merchantID + '\'' +
                ", prepayID='" + prepayID + '\'' +
                ", jsData='" + jsData + '\'' +
                '}';
    }
}
