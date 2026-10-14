/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.util.CommonHelper;

public final class HttpUtils {
    private static int connectTimeout = 500;
    private static int readTimeout = 5000;

    private HttpUtils() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String buildHttpErrorMessage(HttpURLConnection connection) throws IOException {
        StringBuilder messageBuilder = new StringBuilder("(").append(connection.getResponseCode()).append(")");
        if (connection.getResponseMessage() != null) {
            messageBuilder.append(" ");
            messageBuilder.append(connection.getResponseMessage());
        }
        try (InputStreamReader isr = new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);){
            String output;
            messageBuilder.append("[");
            while ((output = br.readLine()) != null) {
                messageBuilder.append(output);
            }
            messageBuilder.append("]");
        }
        finally {
            connection.disconnect();
        }
        return messageBuilder.toString();
    }

    public static HttpURLConnection openPostConnection(URL url2) throws IOException {
        return HttpUtils.openConnection(url2, HttpConstants.HTTP_METHOD.POST.name(), null);
    }

    public static HttpURLConnection openPostConnection(URL url2, Map<String, String> headers) throws IOException {
        return HttpUtils.openConnection(url2, HttpConstants.HTTP_METHOD.POST.name(), headers);
    }

    public static HttpURLConnection openDeleteConnection(URL url2) throws IOException {
        return HttpUtils.openConnection(url2, HttpConstants.HTTP_METHOD.DELETE.name(), null);
    }

    protected static HttpURLConnection openConnection(URL url2, String requestMethod, Map<String, String> headers) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url2.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod(requestMethod);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return connection;
    }

    public static String readBody(HttpURLConnection connection) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);){
            String string;
            try (BufferedReader br = new BufferedReader(isr);){
                String output;
                StringBuilder sb = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                string = sb.toString();
            }
            return string;
        }
    }

    public static String encodeQueryParam(String paramName, String paramValue) {
        return CommonHelper.urlEncode(paramName) + "=" + CommonHelper.urlEncode(paramValue);
    }

    public static void closeConnection(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    public static int getConnectTimeout() {
        return connectTimeout;
    }

    public static void setConnectTimeout(int connectTimeout) {
        HttpUtils.connectTimeout = connectTimeout;
    }

    public static int getReadTimeout() {
        return readTimeout;
    }

    public static void setReadTimeout(int readTimeout) {
        HttpUtils.readTimeout = readTimeout;
    }
}

