/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

public class OnvifMediaProfile {
    public static final String TAG = OnvifMediaProfile.class.getSimpleName();
    private final String name;
    private final String token;

    public OnvifMediaProfile(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public String getToken() {
        return this.token;
    }

    public String toString() {
        return "OnvifMediaProfile{name='" + this.name + "', token='" + this.token + "'}";
    }
}

