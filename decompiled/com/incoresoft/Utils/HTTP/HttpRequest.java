/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.HTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.io.FileUtils;

public class HttpRequest {
    private final String method;
    private final String url;
    private final Map<String, String> headers = new WeakHashMap<String, String>();

    public HttpRequest(String method2, String url2) {
        this.method = method2;
        this.url = url2;
        this.header("User-Agent", "Mozilla/5.0");
    }

    public HttpRequest header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public String send() {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)new URL(this.url).openConnection();
            urlConnection.setRequestMethod(this.method);
            this.headers.forEach(urlConnection::setRequestProperty);
            return HttpRequest.readBuffer(urlConnection);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String send(String body2) {
        return this.send(body2, "application/json");
    }

    public String send(String body2, String contentType) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)new URL(this.url).openConnection();
            urlConnection.setRequestMethod(this.method);
            this.headers.forEach(urlConnection::setRequestProperty);
            urlConnection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());){
                wr.writeBytes(body2);
                wr.flush();
            }
            urlConnection.connect();
            return HttpRequest.readBuffer(urlConnection);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String sendFiles(List<File> files) {
        String LINE_FEED = "\r\n";
        String boundary = "----" + System.nanoTime();
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)new URL(this.url).openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            try (DataOutputStream request = new DataOutputStream(urlConnection.getOutputStream());){
                for (File file : files) {
                    request.writeBytes("--" + boundary + LINE_FEED);
                    request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_FEED);
                    request.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()));
                    request.writeBytes(LINE_FEED);
                    request.writeBytes(LINE_FEED);
                    request.write(FileUtils.readFileToByteArray(file));
                    request.writeBytes(LINE_FEED);
                }
                request.writeBytes("--" + boundary + "--" + LINE_FEED);
                request.flush();
            }
            return HttpRequest.readBuffer(urlConnection);
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
}

