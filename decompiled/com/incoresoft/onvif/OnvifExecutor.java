/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import com.incoresoft.onvif.OnvifXMLBuilder;
import com.incoresoft.onvif.listeners.OnvifResponseListener;
import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.OnvifMediaProfile;
import com.incoresoft.onvif.models.OnvifServices;
import com.incoresoft.onvif.parsers.GetDeviceInformationParser;
import com.incoresoft.onvif.parsers.GetMediaProfilesParser;
import com.incoresoft.onvif.parsers.GetMediaStreamParser;
import com.incoresoft.onvif.parsers.GetPtzStatusParser;
import com.incoresoft.onvif.parsers.GetServicesParser;
import com.incoresoft.onvif.requests.GetDeviceInformationRequest;
import com.incoresoft.onvif.requests.GetMediaProfilesRequest;
import com.incoresoft.onvif.requests.GetMediaStreamRequest;
import com.incoresoft.onvif.requests.GetPtzStatusRequest;
import com.incoresoft.onvif.requests.GetServicesRequest;
import com.incoresoft.onvif.requests.OnvifRequest;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class OnvifExecutor {
    public static final String TAG = OnvifExecutor.class.getSimpleName();
    private final OkHttpClient client;
    private final MediaType reqBodyType;
    private RequestBody reqBody;
    private final Credentials credentials;
    private OnvifResponseListener onvifResponseListener;

    OnvifExecutor(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        this.credentials = new Credentials("username", "password");
        DigestAuthenticator authenticator = new DigestAuthenticator(this.credentials);
        ConcurrentHashMap authCache = new ConcurrentHashMap();
        this.client = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.SECONDS).writeTimeout(100L, TimeUnit.SECONDS).readTimeout(10000L, TimeUnit.SECONDS).build();
        this.reqBodyType = MediaType.parse("application/soap+xml;charset=UTF-8");
    }

    void sendRequestAsync(OnvifDevice device, OnvifRequest request) {
        this.credentials.setUserName(device.getUsername());
        this.credentials.setPassword(device.getPassword());
        String xml = OnvifXMLBuilder.getSoapHeader(this.credentials) + request.getXml() + OnvifXMLBuilder.getEnvelopeEnd();
        this.reqBody = RequestBody.create(this.reqBodyType, xml);
        this.performXmlRequestAsync(device, request, this.buildOnvifRequest(device, request));
    }

    OnvifResponse sendRequest(OnvifDevice device, OnvifRequest request) {
        this.credentials.setUserName(device.getUsername());
        this.credentials.setPassword(device.getPassword());
        String xml = OnvifXMLBuilder.getSoapHeader(this.credentials) + request.getXml() + OnvifXMLBuilder.getEnvelopeEnd();
        System.out.println(xml);
        this.reqBody = RequestBody.create(this.reqBodyType, xml);
        return this.performXmlRequest(device, request, this.buildOnvifRequest(device, request));
    }

    void clear() {
        this.onvifResponseListener = null;
    }

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    private OnvifResponse performXmlRequest(OnvifDevice device, OnvifRequest request, Request xmlRequest) {
        if (xmlRequest == null) {
            return null;
        }
        OnvifResponse response = new OnvifResponse(request);
        try {
            Response xmlResponse = this.client.newCall(xmlRequest).execute();
            ResponseBody xmlBody = xmlResponse.body();
            response.setSuccess(xmlResponse.code() == 200 && xmlBody != null);
            response.setXml(xmlBody != null ? xmlBody.string() : "");
            System.out.println(response.getXml());
            return response;
        }
        catch (IOException e) {
            this.onvifResponseListener.onError(device, -1, e.getMessage());
            return response;
        }
    }

    private void performXmlRequestAsync(final OnvifDevice device, final OnvifRequest request, Request xmlRequest) {
        if (xmlRequest == null) {
            return;
        }
        this.client.newCall(xmlRequest).enqueue(new Callback(){

            @Override
            public void onResponse(Call call, Response xmlResponse) throws IOException {
                OnvifResponse response = new OnvifResponse(request);
                ResponseBody xmlBody = xmlResponse.body();
                if (xmlResponse.code() == 200 && xmlBody != null) {
                    response.setSuccess(true);
                    response.setXml(xmlBody.string());
                    OnvifExecutor.this.parseResponse(device, response);
                    return;
                }
                String errorMessage = "";
                if (xmlBody != null) {
                    errorMessage = xmlBody.string();
                }
                OnvifExecutor.this.onvifResponseListener.onError(device, xmlResponse.code(), errorMessage);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                OnvifExecutor.this.onvifResponseListener.onError(device, -1, e.getMessage());
            }
        });
    }

    private void parseResponse(OnvifDevice device, OnvifResponse response) {
        switch (response.request().getType()) {
            case GET_SERVICES: {
                OnvifServices path = new GetServicesParser().parse(response);
                device.setPath(path);
                ((GetServicesRequest)response.request()).getListener().onServicesReceived(device, path);
                break;
            }
            case GET_DEVICE_INFORMATION: {
                ((GetDeviceInformationRequest)response.request()).getListener().onDeviceInformationReceived(device, new GetDeviceInformationParser().parse(response));
                break;
            }
            case GET_MEDIA_PROFILES: {
                ((GetMediaProfilesRequest)response.request()).getListener().onMediaProfilesReceived(device, (List<OnvifMediaProfile>)new GetMediaProfilesParser().parse(response));
                break;
            }
            case GET_STREAM_URI: {
                GetMediaStreamRequest streamRequest = (GetMediaStreamRequest)response.request();
                streamRequest.getListener().onMediaStreamURIReceived(device, streamRequest.getMediaProfile(), new GetMediaStreamParser().parse(response));
                break;
            }
            case GET_PTZ_STATUS: {
                GetPtzStatusRequest ptzRequest = (GetPtzStatusRequest)response.request();
                ptzRequest.getListener().onGetPtz(device, new GetPtzStatusParser().parse(response));
                break;
            }
            default: {
                this.onvifResponseListener.onResponse(device, response);
            }
        }
    }

    private Request buildOnvifRequest(OnvifDevice device, OnvifRequest request) {
        return new Request.Builder().url(this.getUrlForRequest(device, request)).post(this.reqBody).build();
    }

    private String getUrlForRequest(OnvifDevice device, OnvifRequest request) {
        return device.getHostName() + this.getPathForRequest(device, request);
    }

    private String getPathForRequest(OnvifDevice device, OnvifRequest request) {
        switch (request.getType()) {
            case GET_SERVICES: {
                return device.getPath().getServicesPath();
            }
            case GET_DEVICE_INFORMATION: {
                return device.getPath().getDeviceInformationPath();
            }
            case GET_MEDIA_PROFILES: {
                return device.getPath().getProfilesPath();
            }
            case GET_STREAM_URI: {
                return device.getPath().getStreamURIPath();
            }
        }
        return device.getPath().getServicesPath();
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

