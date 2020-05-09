package com.lehuipay.leona.model;

import com.squareup.moshi.Json;

public class MicropayPaymentResponse extends Payment {

    @Json(name = "hint")
    private String hint;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
