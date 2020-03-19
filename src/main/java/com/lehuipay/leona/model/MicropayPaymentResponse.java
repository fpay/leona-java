package com.lehuipay.leona.model;

import com.alibaba.fastjson.annotation.JSONField;

public class MicropayPaymentResponse extends Payment {

    @JSONField(name = "hint")
    private String hint;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
