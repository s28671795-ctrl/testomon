/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.upnp;

import com.incoresoft.onvif.models.UPnPDevice;
import com.incoresoft.onvif.parsers.UPnPParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import com.incoresoft.onvif.upnp.UPnPDeviceInformation;
import com.incoresoft.onvif.upnp.UPnPDeviceInformationListener;
import com.incoresoft.onvif.upnp.UPnPResponseListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class UPnPExecutor {
    public static final String TAG = UPnPExecutor.class.getSimpleName();
    private final OkHttpClient client;
    private UPnPResponseListener responseListener;

    UPnPExecutor(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
        this.client = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.SECONDS).writeTimeout(100L, TimeUnit.SECONDS).readTimeout(10000L, TimeUnit.SECONDS).build();
    }

    public void sendRequest(UPnPDevice device) {
        this.performXmlRequest(device, this.buildUPnPRequest(device));
    }

    void getDeviceInformation(final UPnPDevice device, final UPnPDeviceInformationListener listener) {
        Request request = this.buildUPnPRequest(device);
        this.client.newCall(request).enqueue(new Callback(){

            @Override
            public void onResponse(Call call, Response xmlResponse) throws IOException {
                ResponseBody xmlBody = xmlResponse.body();
                if (xmlResponse.code() == 200 && xmlBody != null) {
                    UPnPDeviceInformation information = UPnPExecutor.this.parseDeviceInformation(device, xmlBody.string());
                    device.setDeviceInformation(information);
                    listener.onDeviceInformationReceived(device, information);
                    return;
                }
                String errorMessage = "";
                if (xmlBody != null) {
                    errorMessage = xmlBody.string();
                }
                listener.onError(device, xmlResponse.code(), errorMessage);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError(device, -1, e.getMessage());
            }
        });
    }

    public void clear() {
        this.responseListener = null;
    }

    public void setResponseListener(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    private void performXmlRequest(final UPnPDevice device, Request xmlRequest) {
        if (xmlRequest == null) {
            return;
        }
        this.client.newCall(xmlRequest).enqueue(new Callback(){

            @Override
            public void onResponse(Call call, Response xmlResponse) throws IOException {
                ResponseBody xmlBody = xmlResponse.body();
                if (xmlResponse.code() == 200 && xmlBody != null) {
                    UPnPExecutor.this.parseResponse(device, xmlBody.string());
                    return;
                }
                String errorMessage = "";
                if (xmlBody != null) {
                    errorMessage = xmlBody.string();
                }
                UPnPExecutor.this.responseListener.onError(device, xmlResponse.code(), errorMessage);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                UPnPExecutor.this.responseListener.onError(device, -1, e.getMessage());
            }
        });
    }

    private UPnPDeviceInformation parseDeviceInformation(UPnPDevice device, String xmlBody) {
        return new UPnPParser().parse(new OnvifResponse(xmlBody));
    }

    private void parseResponse(UPnPDevice device, String xmlBody) {
        UPnPParser parser = new UPnPParser();
        parser.parse(new OnvifResponse(xmlBody));
    }

    private Request buildUPnPRequest(UPnPDevice device) {
        return new Request.Builder().url(device.getLocation()).addHeader("Content-Type", "text/xml; charset=utf-8").get().build();
    }

    private String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            if (copy.body() != null) {
                copy.body().writeTo(buffer);
            }
            return buffer.readUtf8();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

