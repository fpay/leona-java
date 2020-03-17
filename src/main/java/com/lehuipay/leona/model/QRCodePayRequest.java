package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class QRCodePayRequest {

    public QRCodePayRequest(String merchantID, String terminalID, String orderNo, Integer amount,
                            String notifyURL, String callbackURL, String[] tags) {
        if (CommonUtil.isEmpty(merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePayRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(orderNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePayRequest, merchantID should not be empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.QRCodePayRequest, amount should be greater than zero");
        }
        this.merchantID = merchantID;
        this.terminalID = terminalID;
        this.orderNo = orderNo;
        this.amount = amount;
        this.notifyURL = notifyURL;
        this.callbackURL = callbackURL;
        this.tags = tags;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "terminal_id")
    private String terminalID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "amount")
    private Integer amount;

    @JSONField(name = "notify_url")
    private String notifyURL;

    @JSONField(name = "callback_url")
    private String callbackURL;

    @JSONField(name = "tags")
    private String[] tags;

    public String getMerchantID() {
        return merchantID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public String[] getTags() {
        return tags;
    }
}
