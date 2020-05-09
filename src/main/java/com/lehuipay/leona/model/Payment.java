package com.lehuipay.leona.model;

import com.squareup.moshi.Json;

public class Payment {

    @Json(name = "transaction_id")
    private String transactionID;

    @Json(name = "order_no")
    private String orderNo;

    @Json(name = "upstream_order_no")
    private String upstreamOrderNo;

    @Json(name = "merchant_order_no")
    private String merchantOrderNo;

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "terminal_id")
    private String terminalID;

    @Json(name = "app_id")
    private String appID;

    @Json(name = "buyer_id")
    private String buyerID;

    @Json(name = "amount")
    private Integer amount;

    @Json(name = "status")
    private String status;

    @Json(name = "client_type")
    private String clientType;

    @Json(name = "trade_type")
    private String tradeType;

    @Json(name = "reason")
    private String reason;

    @Json(name = "finished_at")
    private Integer finishedAt;

    @Json(name = "created_at")
    private Integer createdAt;

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

    public String getUpstreamOrderNo() {
        return upstreamOrderNo;
    }

    public void setUpstreamOrderNo(String upstreamOrderNo) {
        this.upstreamOrderNo = upstreamOrderNo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
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

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Integer finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "transactionID='" + transactionID + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", upstreamOrderNo='" + upstreamOrderNo + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", merchantID='" + merchantID + '\'' +
                ", terminalID='" + terminalID + '\'' +
                ", appID='" + appID + '\'' +
                ", buyerID='" + buyerID + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", clientType='" + clientType + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", reason='" + reason + '\'' +
                ", finishedAt=" + finishedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
