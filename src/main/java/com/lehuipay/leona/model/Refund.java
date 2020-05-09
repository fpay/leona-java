package com.lehuipay.leona.model;

import com.squareup.moshi.Json;

public class Refund {

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "terminal_id")
    private String terminalID;

    @Json(name = "refund_id")
    private String refundID;

    @Json(name = "order_no")
    private String orderNo;

    @Json(name = "transaction_id")
    private String transactionID;

    @Json(name = "refund_no")
    private String refundNo;

    @Json(name = "total_amount")
    private String totalAmount;

    @Json(name = "amount")
    private Integer amount;

    @Json(name = "app_id")
    private String appID;

    @Json(name = "buyer_id")
    private String buyerID;

    @Json(name = "status")
    private String status;

    @Json(name = "reason")
    private String reason;

    @Json(name = "client_type")
    private String clientType;

    @Json(name = "trade_type")
    private String tradeType;

    @Json(name = "created_at")
    private Integer createdAt;

    @Json(name = "finished_at")
    private Integer finishedAt;

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

    public String getRefundID() {
        return refundID;
    }

    public void setRefundID(String refundID) {
        this.refundID = refundID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Integer finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "Refund{" +
                "merchantID='" + merchantID + '\'' +
                ", terminalID='" + terminalID + '\'' +
                ", refundID='" + refundID + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", transactionID='" + transactionID + '\'' +
                ", refundNo='" + refundNo + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", amount=" + amount +
                ", appID='" + appID + '\'' +
                ", buyerID='" + buyerID + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", clientType='" + clientType + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", createdAt=" + createdAt +
                ", finishedAt=" + finishedAt +
                '}';
    }
}
