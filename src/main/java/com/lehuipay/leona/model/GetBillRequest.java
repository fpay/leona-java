package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lehuipay.leona.utils.CommonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GetBillRequest {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public GetBillRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBillRequest, merchantID should not be empty");
        }

        if (CommonUtil.isEmpty(builder.date)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBillRequest, date should not be empty");
        }

        try {
            format.setLenient(false);
            format.parse(CommonUtil.NVLL(builder.date));
            if (builder.date.length() != 10) {
                throw new ParseException("", 0);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.GetBillRequest, date format should like 2006-01-02");
        }

        this.merchantID = builder.merchantID;
        this.date = builder.date;
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

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String merchantID;
        private String date;

        public Builder() {}

        public GetBillRequest build(){
            return new GetBillRequest(this);
        }

        public Builder setMerchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }
    }
}
