/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.util;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

public class HTTP {
    public static String content_type = "application/json";
    public static String content_type_text = "application/x-www-form-urlencoded;charset=UTF-8";

    public static String sendPOST(String url2, JSONObject json) {
        return HTTP.sendPOST(url2, json.toJSONString());
    }

    public static String sendPOST(String url2, String properties) {
        try {
            HttpURLConnection con = (HttpURLConnection)new URL(url2).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", "Mozzila Firefox");
            con.setRequestProperty("Content-type", content_type);
            con.setConnectTimeout((int)Duration.ofSeconds(5L).toMillis());
            try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);){
                osw.write(properties);
                osw.flush();
            }
            con.connect();
            return HTTP.readBuffer(con);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readBuffer(HttpURLConnection httpClient) throws IOException {
        String line;
        int responseCode = httpClient.getResponseCode();
        InputStream inputStream2 = 200 <= responseCode && responseCode <= 299 ? httpClient.getInputStream() : httpClient.getErrorStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream2));
        StringBuilder response = new StringBuilder();
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }

    static String urlEncodeUTF8(String s2) {
        return URLEncoder.encode(s2, StandardCharsets.UTF_8);
    }

    static String urlEncodeUTF8(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", HTTP.urlEncodeUTF8(entry.getKey().toString()), HTTP.urlEncodeUTF8(entry.getValue().toString())));
        }
        return sb.toString();
    }

    public static String sendGET(String url2, int timeout2) {
        try {
            HttpURLConnection con = (HttpURLConnection)new URL(url2).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozzila Firefox");
            con.setConnectTimeout((int)Duration.ofMillis(timeout2).toMillis());
            con.connect();
            return HTTP.readBuffer(con);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

