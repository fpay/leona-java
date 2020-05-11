# 乐惠合作伙伴支付平台 Java SDK
LEhuipay OpeN Api SDK for Java

## 引入

```
// maven
<dependency>
    <groupId>com.lehuipay.open</groupId>
    <artifactId>leona</artifactId>
    <version>1.0.0</version>
</dependency>
``` 

## 初始化
```java
Client client = LeonaClient.builder()
      .setAgentID(agent_id)
      .setAgentKey(agent_key)
//      .setAgentPrivateKey(agentPrivateKeyPath)
//      .setLehuipayPublicKey(lehuipayPublicKeyPath)
//      .setSecretKey(secret_key)
//      .setEncryptionLevel(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L1)
//      .setEncryptionAccept(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L0)
       .build();

// 异步
try {
    final QRCodePaymentRequest request = QRCodePaymentRequest.builder()
            .setMerchantID(merchantID)
            .setTerminalID("2")
            .setOrderNo("20200313000000000001")
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
    // 一般而言需要记录日志
    System.err.println(e);
    // 若需要详细错误信息, 请使用如下方法
//    String type = e.getType();
//    String code = e.getCode();
//    String message = e.getMessage();

}

// 同步
try {
    final QRCodePaymentRequest request = QRCodePaymentRequest.builder()
            .setMerchantID(merchantID)
            .setTerminalID("2")
            .setOrderNo("20200313000000000001")
            .setAmount(1)
            .setTags(new String[]{"tag1", "tag2"})
            .build();
    final QRCodePayResponse response = client.createQRCodePayment(request);
    System.out.println(response);
} catch (LeonaException e) {
    System.err.println(e);
}
```