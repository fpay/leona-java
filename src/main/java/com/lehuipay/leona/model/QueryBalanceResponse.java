package com.lehuipay.leona.model;

import com.squareup.moshi.Json;

public class QueryBalanceResponse {

    @Json(name = "merchant_id")
    private String merchantID;

    @Json(name = "amount")
    private Integer amount;

    @Json(name = "frozen_amount")
    private Integer frozenAmount;


    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Integer frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "merchantID='" + merchantID + '\'' +
                ", amount=" + amount +
                ", frozenAmount=" + frozenAmount +
                '}';
    }
}
