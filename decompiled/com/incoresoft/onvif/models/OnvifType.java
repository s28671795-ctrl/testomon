/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

public enum OnvifType {
    CUSTOM(""),
    GET_SERVICES("http://www.onvif.org/ver10/device/wsdl"),
    GET_DEVICE_INFORMATION("http://www.onvif.org/ver10/device/wsdl"),
    GET_MEDIA_PROFILES("http://www.onvif.org/ver10/media/wsdl"),
    GET_STREAM_URI("http://www.onvif.org/ver10/media/wsdl"),
    GET_PTZ_STATUS("http://www.onvif.org/ver20/ptz/wsdl");

    public final String namespace;

    private OnvifType(String namespace) {
        this.namespace = namespace;
    }
}

