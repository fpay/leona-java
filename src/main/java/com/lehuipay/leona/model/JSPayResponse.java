package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;

public class JSPayResponse {

    @JSONField(name = "transaction_id")
    private String transactionID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "prepay_id")
    private String prepayID;

    @JSONField(name = "js_data")
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
