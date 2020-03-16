# 乐惠合作伙伴支付平台 Java SDK
LEhuipay OpeN Api SDK for Java

## 引入

```
// maven
<dependency>
    <groupId>com.github.roujiamo-cold</groupId>
    <artifactId>leona-java</artifactId>
    <version>0.1.1</version>
</dependency>
``` 

## 初始化
```java
Leona client = new LeonaClient
        .Builder(agent_id, agent_key)
//        .setPartnerPriKey(cliPriKeyFilePath)
//        .setLhPubKey(serPubKeyFilePath)
//        .setSecretKey(secret_key)
//        .setEncryptionLevel(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L1)
//        .setEncryptionAccept(Const.HEADER_X_LEHUI_ENCRYPTION_LEVEL_L0)
        .build();

// 异步
try {
    final QRCodePayRequest req =
            new QRCodePayRequest(merchantID, "2", "xxxxxxx", 1, null, null);
    client.qrCodePay(req, (e, data) -> {
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
    final QRCodePayRequest req =
            new QRCodePayRequest(merchantID, "2", "xxxxxxx", 1, null, null);
    final QRCodePayResponse resp = client.qrCodePay(req);
    System.out.println(resp);
} catch (LeonaException e) {
    System.err.println(e);
}
```