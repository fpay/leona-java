package com.lehuipay.leona.contracts;

import com.lehuipay.leona.Callback;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.model.GetBalanceRequest;
import com.lehuipay.leona.model.Balance;
import com.lehuipay.leona.model.GetBillRequest;
import com.lehuipay.leona.model.GetOrderRequest;
import com.lehuipay.leona.model.GetRefundRequest;
import com.lehuipay.leona.model.GetWithdrawalDetailRequest;
import com.lehuipay.leona.model.JSPayRequest;
import com.lehuipay.leona.model.JSPayResponse;
import com.lehuipay.leona.model.MicroPayRequest;
import com.lehuipay.leona.model.Payment;
import com.lehuipay.leona.model.QRCodePayRequest;
import com.lehuipay.leona.model.QRCodePayResponse;
import com.lehuipay.leona.model.Refund;
import com.lehuipay.leona.model.RefundRequest;
import com.lehuipay.leona.model.WithdrawRequest;
import com.lehuipay.leona.model.Withdrawal;

import java.io.OutputStream;

/**
 * 支付业务接口
 */
public interface Client {

    // 二维码支付
    QRCodePayResponse createQRCodePay(QRCodePayRequest req) throws LeonaException;

    void createQRCodePay(QRCodePayRequest req, Callback<QRCodePayResponse> callback) throws LeonaException;

    // 刷卡支付
    Payment createMicroPay(MicroPayRequest req) throws LeonaException;

    void createMicroPay(MicroPayRequest req, Callback<Payment> callback) throws LeonaException;

    // 查询交易
    Payment getOrder(GetOrderRequest req) throws LeonaException;

    void getOrder(GetOrderRequest req, Callback<Payment> callback) throws LeonaException;

    // 退款
    Refund createRefund(RefundRequest req) throws LeonaException;

    void createRefund(RefundRequest req, Callback<Refund> callback) throws LeonaException;

    // 查询退款
    Refund getRefund(GetRefundRequest req) throws LeonaException;

    void getRefund(GetRefundRequest req, Callback<Refund> callback) throws LeonaException;

    // 查询余额
    Balance getBalance(GetBalanceRequest req) throws LeonaException;

    void getBalance(GetBalanceRequest req, Callback<Balance> callback) throws LeonaException;

    // 手动提现
    Withdrawal withdraw(WithdrawRequest req) throws LeonaException;

    void withdraw(WithdrawRequest req, Callback<Withdrawal> callback) throws LeonaException;

    // 查询提现结果
    Withdrawal getWithdrawalDetail(GetWithdrawalDetailRequest req) throws LeonaException;

    void getWithdrawalDetail(GetWithdrawalDetailRequest req, Callback<Withdrawal> callback) throws LeonaException;

    // 账单下载
    void getBill(GetBillRequest req, OutputStream dst) throws LeonaException;

    void getBill(GetBillRequest req, Callback<OutputStream> callback) throws LeonaException;

    // 消费者扫商家二维码支付
    JSPayResponse createJSPay(JSPayRequest req) throws LeonaException;

    void createJSPay(JSPayRequest req, Callback<JSPayResponse> callback) throws LeonaException;
}
