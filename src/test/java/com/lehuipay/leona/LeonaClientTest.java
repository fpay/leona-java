package com.lehuipay.leona;

import com.lehuipay.leona.contracts.Client;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.model.Balance;
import com.lehuipay.leona.model.GetBalanceRequest;
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
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class LeonaClientTest {

    final private static String agent_id = "9527";
    final private static String agent_key = ""; //
    final private static String secret_key = "";
    final private static String merchantID = "";

    final private String cliPriKeyFile = "./src/test/resources/stating_cli_rsa_private_key.pem";
    final private String serPubKeyFile = "./src/test/resources/stating_lh_rsa_public_key.pem";

    Client client;

    @Before
    public void init() {
        client = LeonaClient.builder()
                .setAgentID(agent_id)
                .setAgentKey(agent_key)
//                .setPartnerPriKey(cliPriKeyFile)
//                .setLhPubKey(serPubKeyFile)
//                .setSecretKey(secret_key)
//                .setEncryptionLevel(Const.HEADER_ENCRYPTION_LEVEL_L1)
//                .setEncryptionLevel(Const.HEADER_ENCRYPTION_LEVEL_L2)
//                .setEncryptionAccept(Const.HEADER_ENCRYPTION_LEVEL_L0)
                .build();
    }

    @Test
    public void testQRCodePay() {
        long start = System.currentTimeMillis();
        try {
            final QRCodePayRequest request = QRCodePayRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200313000000000001")
                    .setAmount(1)
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            final QRCodePayResponse response = client.createQRCodePay(request);
            final String url = response.getUrl();
            System.out.println(response);
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testQRCodePayCallback() {
        long start = System.currentTimeMillis();
        try {
            final QRCodePayRequest request = QRCodePayRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200313000000000001")
                    .setAmount(1)
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            client.createQRCodePay(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMicroPayPay() {
        long start = System.currentTimeMillis();
        try {
            final MicroPayRequest request = MicroPayRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200313000000000003")
                    .setAmount(2)
                    .setAuthCode("281848450128437300")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            final Payment response = client.createMicroPay(request);
            System.out.println(response);

            switch (response.getStatus()) {
                case Const.PAYMENT_STATUS_PROCESSING:
                    // check payment status few seconds later
                    break;
                case Const.PAYMENT_STATUS_SUCCEEDED:
                    // save payment result
                    final String merchantOrderNo = response.getMerchantOrderNo();
                    final Date finishedAt = new Date(response.getFinishedAt());
                    break;
                default:
                    // handle failed payment
            }
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testMicroPayPayCallback() {
        long start = System.currentTimeMillis();
        try {
            final MicroPayRequest request = MicroPayRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200313000000000003")
                    .setAmount(2)
                    .setAuthCode("281848450128437300")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            client.createMicroPay(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);

                switch (data.getStatus()) {
                    case Const.PAYMENT_STATUS_PROCESSING:
                        // check payment status few seconds later
                        break;
                    case Const.PAYMENT_STATUS_SUCCEEDED:
                        // save payment result
                        final String merchantOrderNo = data.getMerchantOrderNo();
                        final Date finishedAt = new Date(data.getFinishedAt());
                        break;
                    default:
                        // handle failed payment
                }
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetOrder() {
        try {
            final GetOrderRequest request = GetOrderRequest.builder()
                    .setMerchantID(merchantID)
                    .setOrderNo("20200313000000000004")
                    .setTransactionID(null)
                    .build();
            final Payment response = client.getOrder(request);
            System.out.println(response);

            switch (response.getStatus()) {
                case Const.PAYMENT_STATUS_PROCESSING:
                    // check payment status few seconds later
                    break;
                case Const.PAYMENT_STATUS_SUCCEEDED:
                    // save payment result
                    final String merchantOrderNo = response.getMerchantOrderNo();
                    final Date finishedAt = new Date(response.getFinishedAt());
                    break;
                default:
                    // handle failed payment
            }
        } catch (LeonaException e) {
//            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Test
    public void testGetOrderCallback() {
        long start = System.currentTimeMillis();
        final GetOrderRequest request = GetOrderRequest.builder()
                .setMerchantID(merchantID)
                .setOrderNo("20200313000000000004")
                .setTransactionID(null)
                .build();
        try {
            client.getOrder(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);

                switch (data.getStatus()) {
                    case Const.PAYMENT_STATUS_PROCESSING:
                        // check payment status few seconds later
                        break;
                    case Const.PAYMENT_STATUS_SUCCEEDED:
                        // save payment result
                        final String merchantOrderNo = data.getMerchantOrderNo();
                        final Date finishedAt = new Date(data.getFinishedAt());
                        break;
                    default:
                        // handle failed payment
                }
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }

        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRefund() {
        long start = System.currentTimeMillis();
        try {
            final RefundRequest request = RefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setOrderNo("20200313000000000004")
                    .setRefundNo("Re_20200313000000000004")
                    .setAmount(1)
                    .build();
            final Refund response = client.createRefund(request);
            System.out.println(response);

            switch (response.getStatus()) {
                case Const.REFUND_STATUS_CREATED:
                    break;
                case Const.REFUND_STATUS_PROCESSING:
                    // check payment status few seconds later
                    break;
                case Const.REFUND_STATUS_SUCCEEDED:
                    // save payment result
                    break;
                default:
                    // handle failed payment
            }
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testRefundCallback() {
        long start = System.currentTimeMillis();
        try {
            final RefundRequest request = RefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setOrderNo("20200313000000000004")
                    .setRefundNo("Re_20200313000000000004")
                    .setAmount(1)
                    .build();
            client.createRefund(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);

                switch (data.getStatus()) {
                    case Const.REFUND_STATUS_CREATED:
                        break;
                    case Const.REFUND_STATUS_PROCESSING:
                        // check payment status few seconds later
                        break;
                    case Const.REFUND_STATUS_SUCCEEDED:
                        // save payment result
                        break;
                    default:
                        // handle failed payment
                }
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRefund() {
        long start = System.currentTimeMillis();
        try {
            final GetRefundRequest request = GetRefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setRefundNo("207712193487548001010")
                    .build();
            final Refund response = client.getRefund(request);
            System.out.println(response);

            switch (response.getStatus()) {
                case Const.REFUND_STATUS_CREATED:
                    break;
                case Const.REFUND_STATUS_PROCESSING:
                    // check payment status few seconds later
                    break;
                case Const.REFUND_STATUS_SUCCEEDED:
                    // save payment result
                    break;
                default:
                    // handle failed payment
            }
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testGetRefundCallback() {
        long start = System.currentTimeMillis();
        try {
            final GetRefundRequest request = GetRefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setRefundNo("207712193487548001010")
                    .build();
            client.getRefund(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);

                switch (data.getStatus()) {
                    case Const.REFUND_STATUS_CREATED:
                        break;
                    case Const.REFUND_STATUS_PROCESSING:
                        // check payment status few seconds later
                        break;
                    case Const.REFUND_STATUS_SUCCEEDED:
                        // save payment result
                        break;
                    default:
                        // handle failed payment
                }
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBalance() {
        long start = System.currentTimeMillis();
        try {
            final GetBalanceRequest request = GetBalanceRequest.builder().setMerchantID(merchantID).build();
            final Balance response = client.getBalance(request);
            System.out.println(response);
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testGetBalanceCallback() {
        long start = System.currentTimeMillis();
        try {
            final GetBalanceRequest request = GetBalanceRequest.builder().setMerchantID(merchantID).build();
            client.getBalance(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithdraw() {
        long start = System.currentTimeMillis();
        try {
            final WithdrawRequest request = WithdrawRequest.builder()
                    .setMerchantID(merchantID)
                    .setRequestID("20200313000000000003")
                    .setAmount(1000)
                    .build();
            final Withdrawal resp = client.withdraw(request);
            System.out.println(resp);
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testWithdrawCallback() {
        long start = System.currentTimeMillis();
        try {
            final WithdrawRequest request = WithdrawRequest.builder()
                    .setMerchantID(merchantID)
                    .setRequestID("20200313000000000003")
                    .setAmount(1000)
                    .build();
            client.withdraw(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetWithdrawalDetail() {
        long start = System.currentTimeMillis();
        try {
            final GetWithdrawalDetailRequest request = GetWithdrawalDetailRequest.builder()
                    .setMerchantID(merchantID)
                    .setRequestID("20200311201810003")
                    .build();
            final Withdrawal response = client.getWithdrawalDetail(request);
            System.out.println(response);

            switch (response.getStatus()) {
                case Const.WITHDRAWAL_STATUS_ACCEPTED:
                    break;
                case Const.WITHDRAWAL_STATUS_PROCESSING:
                    // query withdrawal status later
                    break;
                case Const.WITHDRAWAL_STATUS_SUCCEEDED:
                    // save result
                    break;
                case Const.WITHDRAWAL_STATUS_FAILED:
                    // handle failed withdrawal
                    final String reason = response.getReason();
                    break;
            }
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testGetWithdrawalDetailCallback() {
        long start = System.currentTimeMillis();
        try {
            final GetWithdrawalDetailRequest request = GetWithdrawalDetailRequest.builder()
                    .setMerchantID(merchantID)
                    .setRequestID("20200311201810003")
                    .build();
            client.getWithdrawalDetail(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);

                switch (data.getStatus()) {
                    case Const.WITHDRAWAL_STATUS_ACCEPTED:
                        break;
                    case Const.WITHDRAWAL_STATUS_PROCESSING:
                        // query withdrawal status later
                        break;
                    case Const.WITHDRAWAL_STATUS_SUCCEEDED:
                        // save result
                        break;
                    case Const.WITHDRAWAL_STATUS_FAILED:
                        // handle failed withdrawal
                        final String reason = data.getReason();
                        break;
                }
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateJSPay() {
        long start = System.currentTimeMillis();
        try {
            final JSPayRequest request = JSPayRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("10093")
                    .setOrderNo("202003180000000000003")
                    .setAmount(1)
                    .setClientType(Const.CLIENT_TYPE_WEIXIN)
                    .setAppID("wxb3eb769caaf296c8")
                    .setBuyerID("oqv4cxFArU7VW3olcZV8unvfzMpA")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            final JSPayResponse response = client.createJSPay(request);
            final String transactionID = response.getTransactionID();
            final String jsData = response.getJsData();
            System.out.println(response);
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testCreateJSPayCallback() {
        long start = System.currentTimeMillis();
        try {
            final JSPayRequest request = JSPayRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("10093")
                    .setOrderNo("202003180000000000001")
                    .setAmount(1)
                    .setClientType(Const.CLIENT_TYPE_WEIXIN)
                    .setAppID("wxb3eb769caaf296c8")
                    .setBuyerID("oqv4cxFArU7VW3olcZV8unvfzMpA")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            client.createJSPay(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data);
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBill() {
        long start = System.currentTimeMillis();
        try {
            final GetBillRequest request = GetBillRequest.builder()
                    .setMerchantID(merchantID)
                    .setDate("2020-03-03")
                    .build();
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            client.getBill(request, outputStream);
            System.out.println(outputStream.toString());
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testGetBillCallback() {
        long start = System.currentTimeMillis();
        try {
            final GetBillRequest request = GetBillRequest.builder()
                    .setMerchantID(merchantID)
                    .setDate("2020-03-03")
                    .build();
            client.getBill(request, (e, data) -> {
                if (e != null) {
                    System.err.println(e);
                    return;
                }
                System.out.println(data.toString());
            });
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

