package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;

public class QRCodePaymentResponse {

    @JSONField(name = "transaction_id")
    private String transactionID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "url")
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "QRCodePayResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", merchantID='" + merchantID + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
