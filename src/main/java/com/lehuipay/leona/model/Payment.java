package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Payment {

    @JSONField(name = "transaction_id")
    private String transactionID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "upstream_order_no")
    private String upstreamOrderNo;

    @JSONField(name = "merchant_order_no")
    private String merchantOrderNo;

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "terminal_id")
    private String terminalID;

    @JSONField(name = "app_id")
    private String appID;

    @JSONField(name = "buyer_id")
    private String buyerID;

    @JSONField(name = "amount")
    private Integer amount;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "client_type")
    private String clientType;

    @JSONField(name = "trade_type")
    private String tradeType;

    @JSONField(name = "hint")
    private String hint;

    @JSONField(name = "reason")
    private String reason;

    @JSONField(name = "finished_at")
    private Integer finishedAt;

    @JSONField(name = "created_at")
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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
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
                ", hint='" + hint + '\'' +
                ", reason='" + reason + '\'' +
                ", finishedAt=" + finishedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
