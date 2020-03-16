package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GetBillRequest {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public GetBillRequest(String merchantID, String date) {
        if (CommonUtil.isEmpty(merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBillRequest, merchantID should not be empty");
        }

        if (CommonUtil.isEmpty(date)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBillRequest, date should not be empty");
        }

        try {
            format.setLenient(false);
            format.parse(CommonUtil.NVLL(date));
            if (date.length() != 10) {
                throw new ParseException("", 0);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBillRequest, date format should like 2006-01-02");
        }

        this.merchantID = merchantID;
        this.date = date;
    }

    @JSONField(name = "merchant_id")
    private String merchantID;

    @JSONField(name = "date")
    private String date;

    public String getMerchantID() {
        return merchantID;
    }

    public String getDate() {
        return date;
    }
}
