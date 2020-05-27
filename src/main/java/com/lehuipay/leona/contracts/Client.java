package com.lehuipay.leona.contracts;

import com.lehuipay.leona.Callback;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.model.MicropayPaymentResponse;
import com.lehuipay.leona.model.QueryBalanceRequest;
import com.lehuipay.leona.model.QueryBalanceResponse;
import com.lehuipay.leona.model.DownloadBillsRequest;
import com.lehuipay.leona.model.QueryPaymentRequest;
import com.lehuipay.leona.model.QueryPaymentResponse;
import com.lehuipay.leona.model.QueryRefundRequest;
import com.lehuipay.leona.model.QueryRefundResponse;
import com.lehuipay.leona.model.QueryWithdrawalRequest;
import com.lehuipay.leona.model.JspayPaymentRequest;
import com.lehuipay.leona.model.JspayPaymentResponse;
import com.lehuipay.leona.model.MicropayPaymentRequest;
import com.lehuipay.leona.model.QRCodePaymentRequest;
import com.lehuipay.leona.model.QRCodePaymentResponse;
import com.lehuipay.leona.model.RefundRequest;
import com.lehuipay.leona.model.RefundResponse;
import com.lehuipay.leona.model.WithdrawRequest;
import com.lehuipay.leona.model.QueryWithdrawalResponse;
import com.lehuipay.leona.model.WithdrawResponse;

import java.io.OutputStream;

/**
 * 支付业务接口
 */
public interface Client {

    // 二维码支付
    QRCodePaymentResponse createQRCodePayment(QRCodePaymentRequest req) throws LeonaException;

    void createQRCodePayment(QRCodePaymentRequest req, Callback<QRCodePaymentResponse> callback);

    // 刷卡支付
    MicropayPaymentResponse createMicropayPayment(MicropayPaymentRequest req) throws LeonaException;

    void createMicropayPayment(MicropayPaymentRequest req, Callback<MicropayPaymentResponse> callback);

    // 查询交易
    QueryPaymentResponse queryPayment(QueryPaymentRequest req) throws LeonaException;

    void queryPayment(QueryPaymentRequest req, Callback<QueryPaymentResponse> callback);

    // 退款
    RefundResponse createRefund(RefundRequest req) throws LeonaException;

    void createRefund(RefundRequest req, Callback<RefundResponse> callback);

    // 查询退款
    QueryRefundResponse queryRefund(QueryRefundRequest req) throws LeonaException;

    void queryRefund(QueryRefundRequest req, Callback<QueryRefundResponse> callback);

    // 查询余额
    QueryBalanceResponse queryBalance(QueryBalanceRequest req) throws LeonaException;

    void queryBalance(QueryBalanceRequest req, Callback<QueryBalanceResponse> callback);

    // 手动提现
    WithdrawResponse withdraw(WithdrawRequest req) throws LeonaException;

    void withdraw(WithdrawRequest req, Callback<WithdrawResponse> callback);

    // 查询提现结果
    QueryWithdrawalResponse queryWithdrawal(QueryWithdrawalRequest req) throws LeonaException;

    void queryWithdrawal(QueryWithdrawalRequest req, Callback<QueryWithdrawalResponse> callback);

    // 账单下载
    void downloadBills(DownloadBillsRequest req, OutputStream dst) throws LeonaException;

    void downloadBills(DownloadBillsRequest req, Callback<OutputStream> callback);

    // 消费者扫商家二维码支付
    JspayPaymentResponse createJspayPayment(JspayPaymentRequest req) throws LeonaException;

    void createJspayPayment(JspayPaymentRequest req, Callback<JspayPaymentResponse> callback);
}
