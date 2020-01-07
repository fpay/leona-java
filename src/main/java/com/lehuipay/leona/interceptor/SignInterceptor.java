package com.lehuipay.leona.interceptor;

import com.lehuipay.leona.Const;
import com.lehuipay.leona.contracts.Signer;
import com.lehuipay.leona.exception.LeonaRuntimeException;
import com.lehuipay.leona.utils.CommonUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SignInterceptor implements Interceptor {

    private final MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");

    public SignInterceptor(Signer signer, String agentID) {
        this.signer = signer;
        this.agentID = agentID;
    }

    private Signer signer;

    private String agentID;

    private String generateNonce() {
        return CommonUtil.randomStr(Const.NONCE_MIN_LENGTH, Const.NONCE_MAX_LENTH);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        final String body = InterceptorHelper.requestBody2String(request);

        // request加签
        final String nonce = generateNonce();
        final String sign = signer.sign(body, nonce);

        request = request.newBuilder()
                .addHeader(Const.HEADER_X_LEHUI_AGENTID, agentID)
                .addHeader(Const.HEADER_X_LEHUI_NONCE, nonce)
                .addHeader(Const.HEADER_X_LEHUI_SIGNATURE, sign)
                .build();

        final Response response = chain.proceed(request);

        // response验签
        String content = response.body().string();
        final String respBody = CommonUtil.NVLL(content);
        final String respNonce = CommonUtil.NVLL(response.header(Const.HEADER_X_LEHUI_NONCE));
        final String respSignature = CommonUtil.NVLL(response.header(Const.HEADER_X_LEHUI_SIGNATURE));

        if (!signer.verify(respBody, respNonce, respSignature)) {
            throw new LeonaRuntimeException("invalid response signature");
        }

        return response.newBuilder()
                .body(ResponseBody.create(content, mediaTypeJSON))
                .build();
    }
}
