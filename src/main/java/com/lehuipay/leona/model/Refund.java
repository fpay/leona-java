package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Refund {

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "terminal_id")
    private String terminalID;

    @JSONField(name = "refund_id")
    private String refundID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "transaction_id")
    private String transactionID;

    @JSONField(name = "refund_no")
    private String refundNo;

    @JSONField(name = "total_amount")
    private String totalAmount;

    @JSONField(name = "amount")
    private Integer amount;

    @JSONField(name = "app_id")
    private String appID;

    @JSONField(name = "buyer_id")
    private String buyerID;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "reason")
    private String reason;

    @JSONField(name = "client_type")
    private String clientType;

    @JSONField(name = "trade_type")
    private String tradeType;

    @JSONField(name = "created_at")
    private Integer createdAt;

    @JSONField(name = "finished_at")
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
