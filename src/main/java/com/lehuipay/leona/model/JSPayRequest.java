package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

import java.util.HashSet;

public class JSPayRequest {

    private HashSet clientTypeSet =  new HashSet() {
        {
            add("weixin");
            add("alipay");
            add("unionpay");
            add("jdpay");
        }
    };

    public JSPayRequest(String merchantID, String terminalID, String orderNo, Integer amount, String clientType,
                        String appID, String buyerID, String clientIP, String notifyURL) {
        if (CommonUtil.isEmpty(merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(orderNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, orderNo should not be empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, amount should be greater than zero");
        }
        if (CommonUtil.isEmpty(clientType)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, clientType should not be empty");
        }
        if (!clientTypeSet.contains(clientType)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, clientType should be one of [weixin, alipay, unionpay, jdpay]");
        }
        if (CommonUtil.isEmpty(appID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, appID should not be empty");
        }
        if (CommonUtil.isEmpty(buyerID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, buyerID should not be empty");
        }
        if (CommonUtil.isEmpty(clientIP)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.JSPayRequest, clientIP should not be empty");
        }

        this.merchantID = merchantID;
        this.terminalID = terminalID;
        this.orderNo = orderNo;
        this.amount = amount;
        this.clientType = clientType;
        this.appID = appID;
        this.buyerID = buyerID;
        this.clientIP = clientIP;
        this.notifyURL = notifyURL;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "terminal_id")
    private String terminalID;

    @JSONField(name = "order_no")
    private String orderNo;

    @JSONField(name = "amount")
    private Integer amount;

    @JSONField(name = "client_type")
    private String clientType;

    @JSONField(name = "app_id")
    private String appID;

    @JSONField(name = "buyer_id")
    private String buyerID;

    @JSONField(name = "client_ip")
    private String clientIP;

    @JSONField(name = "notify_url")
    private String notifyURL;

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

    public String getClientType() {
        return clientType;
    }

    public String getAppID() {
        return appID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getNotifyURL() {
        return notifyURL;
    }
}
