/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

import com.incoresoft.onvif.models.DeviceType;
import java.util.Locale;

public abstract class Device
implements Comparable<Device> {
    public static final String TAG = Device.class.getSimpleName();
    private static final String FORMAT_HTTP = "http://%s";
    private String host;
    private String username;
    private String password;
    private boolean connected;

    public Device(String host2) {
        this(host2, "", "");
    }

    public Device(String host2, String username, String password) {
        this.host = this.buildUrl(host2);
        this.username = username;
        this.password = password;
    }

    public String getHostName() {
        return this.host;
    }

    public void setHostName(String url2) {
        this.host = url2;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnected(boolean connected2) {
        this.connected = connected2;
    }

    @Override
    public int compareTo(Device device) {
        return this.getHostName().compareTo(device.getHostName());
    }

    private String buildUrl(String url2) {
        if (url2.startsWith("http://") || url2.startsWith("https://")) {
            return url2;
        }
        return String.format(Locale.getDefault(), FORMAT_HTTP, url2);
    }

    public abstract DeviceType getType();
}

