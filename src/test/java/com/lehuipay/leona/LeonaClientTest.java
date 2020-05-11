package com.lehuipay.leona;

import com.lehuipay.leona.contracts.Client;
import com.lehuipay.leona.exception.LeonaException;
import com.lehuipay.leona.model.MicropayPaymentResponse;
import com.lehuipay.leona.model.QueryBalanceResponse;
import com.lehuipay.leona.model.QueryBalanceRequest;
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
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class LeonaClientTest {

    final private static String agent_id = "9527";
    final private static String agent_key = "6nQjbXqPC;GvW3H%";
    final private static String secret_key = "";
    final private static String merchantID = "460";

    final private String agentPrivateKeyFile = "./src/test/resources/stating_cli_rsa_private_key.pem";
    final private String lehuipayPublicKeyFile = "./src/test/resources/stating_lh_rsa_public_key.pem";

//    final private String agentPrivateKeyFile = "./src/test/resources/pkcs8_cli_rsa_private_key.pem";
//    final private String lehuipayPublicKeyFile = "./src/test/resources/pkcs8_lh_rsa_public_key.pem";

    Client client;

    @Before
    public void init() throws IOException {
        client = LeonaClient.builder()
                .setAgentID(agent_id)
                .setAgentKey(agent_key)
                .setAgentPrivateKey(agentPrivateKeyFile)
                .setLehuipayPublicKey(lehuipayPublicKeyFile)
//                .setSecretKey(secret_key)
                .setEncryptionLevel(Const.HEADER_ENCRYPTION_LEVEL_L1)
//                .setEncryptionLevel(Const.HEADER_ENCRYPTION_LEVEL_L2)
//                .setEncryptionAccept(Const.HEADER_ENCRYPTION_LEVEL_L0)
                .build();
    }

    @Test
    public void testCreateQRCodePayment() {
        long start = System.currentTimeMillis();
        try {
            final QRCodePaymentRequest request = QRCodePaymentRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200313000000000001")
                    .setAmount(1)
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            final QRCodePaymentResponse response = client.createQRCodePayment(request);
            final String url = response.getUrl();
            System.out.println(response);
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testCreateQRCodePaymentCallback() {
        long start = System.currentTimeMillis();
        try {
            final QRCodePaymentRequest request = QRCodePaymentRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200413000000000002")
                    .setAmount(1)
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            client.createQRCodePayment(request, (e, data) -> {
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
    public void testCreateMicropayPayment() {
        long start = System.currentTimeMillis();
        try {
            final MicropayPaymentRequest request = MicropayPaymentRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200313000000001003")
                    .setAmount(2)
                    .setAuthCode("134575286096065622")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            final MicropayPaymentResponse response = client.createMicropayPayment(request);
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
    public void testCreateMicropayPaymentCallback() {
        long start = System.currentTimeMillis();
        try {
            final MicropayPaymentRequest request = MicropayPaymentRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("2")
                    .setOrderNo("20200413000000000003")
                    .setAmount(2)
                    .setAuthCode("134518567872081940")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            client.createMicropayPayment(request, (e, data) -> {
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
    public void testQueryPayment() {
        try {
            final QueryPaymentRequest request = QueryPaymentRequest.builder()
                    .setMerchantID(merchantID)
                    .setOrderNo("20200313000000000009")
                    .setTransactionID(null)
                    .build();
            final QueryPaymentResponse response = client.queryPayment(request);
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
    public void testQueryPaymentCallback() {
        long start = System.currentTimeMillis();
        final QueryPaymentRequest request = QueryPaymentRequest.builder()
                .setMerchantID(merchantID)
                .setOrderNo("20200313000000000004")
                .setTransactionID(null)
                .build();
        try {
            client.queryPayment(request, (e, data) -> {
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
    public void testCreateRefund() {
        long start = System.currentTimeMillis();
        try {
            final RefundRequest request = RefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setOrderNo("20200313000000000009")
                    .setRefundNo("Re_20200313000000000009")
                    .setAmount(1)
                    .build();
            final RefundResponse response = client.createRefund(request);
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
    public void testCreateRefundCallback() {
        long start = System.currentTimeMillis();
        try {
            final RefundRequest request = RefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setOrderNo("20200413000000000003")
                    .setRefundNo("Re_20200413000000000003")
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
    public void testQueryRefund() {
        long start = System.currentTimeMillis();
        try {
            final QueryRefundRequest request = QueryRefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setRefundNo("Re_20200413000000000003")
                    .build();
            final QueryRefundResponse response = client.queryRefund(request);
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
    public void testQueryRefundCallback() {
        long start = System.currentTimeMillis();
        try {
            final QueryRefundRequest request = QueryRefundRequest.builder()
                    .setMerchantID(merchantID)
                    .setRefundNo("Re_20200413000000000003")
                    .build();
            client.queryRefund(request, (e, data) -> {
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
    public void testQueryBalance() {
        long start = System.currentTimeMillis();
        try {
            final QueryBalanceRequest request = QueryBalanceRequest.builder().setMerchantID(merchantID).build();
            final QueryBalanceResponse response = client.queryBalance(request);
            System.out.println(response);
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testQueryBalanceCallback() {
        long start = System.currentTimeMillis();
        try {
            final QueryBalanceRequest request = QueryBalanceRequest.builder().setMerchantID(merchantID).build();
            client.queryBalance(request, (e, data) -> {
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
            final WithdrawResponse resp = client.withdraw(request);
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
    public void testQueryWithdrawal() {
        long start = System.currentTimeMillis();
        try {
            final QueryWithdrawalRequest request = QueryWithdrawalRequest.builder()
                    .setMerchantID(merchantID)
                    .setRequestID("20200311201810003")
                    .build();
            final QueryWithdrawalResponse response = client.queryWithdrawal(request);
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
    public void testQueryWithdrawalCallback() {
        long start = System.currentTimeMillis();
        try {
            final QueryWithdrawalRequest request = QueryWithdrawalRequest.builder()
                    .setMerchantID(merchantID)
                    .setRequestID("20200311201810003")
                    .build();
            client.queryWithdrawal(request, (e, data) -> {
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
    public void testCreateJspayPayment() {
        long start = System.currentTimeMillis();
        try {
            final JspayPaymentRequest request = JspayPaymentRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("10093")
                    .setOrderNo("202003180000000023003")
                    .setAmount(1)
                    .setClientType(Const.CLIENT_TYPE_WEIXIN)
                    .setAppID("wxb3eb769caaf296c8")
                    .setBuyerID("oqv4cxFArU7VW3olcZV8unvfzMpA")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            final JspayPaymentResponse response = client.createJspayPayment(request);
            final String transactionID = response.getTransactionID();
            final String jsData = response.getJsData();
            System.out.println(response);
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testCreateJspayPaymentCallback() {
        long start = System.currentTimeMillis();
        try {
            final JspayPaymentRequest request = JspayPaymentRequest.builder()
                    .setMerchantID(merchantID)
                    .setTerminalID("10093")
                    .setOrderNo("202004180000000033003")
                    .setAmount(1)
                    .setClientType(Const.CLIENT_TYPE_WEIXIN)
                    .setAppID("wxb3eb769caaf296c8")
                    .setBuyerID("oqv4cxFArU7VW3olcZV8unvfzMpA")
                    .setClientIP("127.0.0.1")
                    .setTags(new String[]{"tag1", "tag2"})
                    .build();
            client.createJspayPayment(request, (e, data) -> {
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
    public void testDownloadBills() {
        long start = System.currentTimeMillis();
        try {
            final DownloadBillsRequest request = DownloadBillsRequest.builder()
                    .setMerchantID(merchantID)
                    .setDate("2020-05-03")
                    .build();
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            client.downloadBills(request, outputStream);
            System.out.println(outputStream.toString());
        } catch (LeonaException e) {
            System.err.println(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testDownloadBillsCallback() {
        long start = System.currentTimeMillis();
        try {
            final DownloadBillsRequest request = DownloadBillsRequest.builder()
                    .setMerchantID(merchantID)
                    .setDate("2020-03-03")
                    .build();
            client.downloadBills(request, (e, data) -> {
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

