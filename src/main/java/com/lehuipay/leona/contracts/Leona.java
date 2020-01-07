package com.lehuipay.leona.contracts;

import com.lehuipay.leona.Callback;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.model.GetOrderRequest;
import com.lehuipay.leona.model.GetRefundRequest;
import com.lehuipay.leona.model.MicroPayRequest;
import com.lehuipay.leona.model.Payment;
import com.lehuipay.leona.model.QRCodePayRequest;
import com.lehuipay.leona.model.QRCodePayResponse;
import com.lehuipay.leona.model.Refund;
import com.lehuipay.leona.model.RefundRequest;

/**
 * 支付业务接口
 */
public interface Leona {

    // 二维码支付
    QRCodePayResponse qrCodePay(QRCodePayRequest req) throws LeonaException;

    void qrCodePay(QRCodePayRequest req, Callback<QRCodePayResponse> callback) throws LeonaException;

    // 刷卡支付
    Payment microPay(MicroPayRequest req) throws LeonaException;

    void microPay(MicroPayRequest req, Callback<Payment> callback) throws LeonaException;

    // 查询交易
    Payment getOrder(GetOrderRequest req) throws LeonaException;

    void getOrder(GetOrderRequest req, Callback<Payment> callback) throws LeonaException;

    // 退款
    Refund refund(RefundRequest req) throws LeonaException;

    void refund(RefundRequest req, Callback<Refund> callback) throws LeonaException;

    // 查询退款
    Refund getRefund(GetRefundRequest req) throws LeonaException;

    void getRefund(GetRefundRequest req, Callback<Refund> callback) throws LeonaException;
}
