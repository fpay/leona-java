package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

public class MicroPayRequest {

    public MicroPayRequest(String merchantID, String terminalID, String orderNo, Integer amount, String authCode,
                           String notifyURL) {
        if (CommonUtil.isEmpty(merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicroPayRequest, merchantID should not be empty");
        }
        if (CommonUtil.isEmpty(terminalID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicroPayRequest, terminalID should not be empty");
        }
        if (CommonUtil.isEmpty(orderNo)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicroPayRequest, orderNo should not be empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicroPayRequest, amount should be greater than zero");
        }
        if (CommonUtil.isEmpty(authCode)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.MicroPayRequest, authCode should not be empty");
        }

        this.merchantID = merchantID;
        this.terminalID = terminalID;
        this.orderNo = orderNo;
        this.amount = amount;
        this.authCode = authCode;
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

    @JSONField(name = "auth_code")
    private String authCode;

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

    public String getAuthCode() {
        return authCode;
    }

    public String getNotifyURL() {
        return notifyURL;
    }
}
