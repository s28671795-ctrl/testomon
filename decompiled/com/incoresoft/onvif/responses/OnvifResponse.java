/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.responses;

import com.incoresoft.onvif.requests.OnvifRequest;

public class OnvifResponse<T> {
    public static final String TAG = OnvifResponse.class.getSimpleName();
    private boolean success;
    private int errorCode;
    private String errorMessage;
    private String xml;
    private OnvifRequest onvifRequest;

    public OnvifResponse(String xml) {
        this.xml = xml;
    }

    public OnvifResponse(OnvifRequest onvifRequest) {
        this.onvifRequest = onvifRequest;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getXml() {
        return this.xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public OnvifRequest request() {
        return this.onvifRequest;
    }
}

