package com.lehuipay.leona.model;

import com.lehuipay.leona.utils.CommonUtil;
import com.squareup.moshi.Json;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DownloadBillsRequest {

    public DownloadBillsRequest(Builder builder) {
        if (CommonUtil.isEmpty(builder.merchantID)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.DownloadBillsRequest, merchantID should not be empty");
        }

        if (CommonUtil.isEmpty(builder.date)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.DownloadBillsRequest, date should not be empty");
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            format.parse(CommonUtil.NVLL(builder.date));
            if (builder.date.length() != 10) {
                throw new ParseException("", 0);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("init com.lehuipay.leona.model.DownloadBillsRequest, date format should like 2006-01-02");
        }

        this.merchantID = builder.merchantID;
        this.date = builder.date;
    }

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "date")
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

        public DownloadBillsRequest build(){
            return new DownloadBillsRequest(this);
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
