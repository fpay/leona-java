package com.lehuipay.leona.interceptor;

import okhttp3.Request;
import okio.Buffer;

import java.io.IOException;

public class InterceptorHelper {

    public static String requestBody2String(Request request) throws IOException {
        final Buffer buffer = new Buffer();
        final Request copy = request.newBuilder().build();
        copy.body().writeTo(buffer);
        return buffer.readUtf8();
    }
}
