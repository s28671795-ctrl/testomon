/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

import com.incoresoft.onvif.models.Device;
import com.incoresoft.onvif.models.DeviceType;
import com.incoresoft.onvif.upnp.UPnPDeviceInformation;

public class UPnPDevice
extends Device {
    public static final String TAG = UPnPDevice.class.getSimpleName();
    private String header;
    private String location;
    private String server;
    private String usn;
    private String st;
    private UPnPDeviceInformation deviceInformation = new UPnPDeviceInformation();

    public UPnPDevice(String hostName) {
        this(hostName, "", "", "", "", "");
    }

    public UPnPDevice(String hostName, String header, String location, String server, String usn, String st) {
        super(hostName);
        this.initHeaders(header, location, server, usn, st);
    }

    private void initHeaders(String header, String location, String server, String usn, String st) {
        this.header = header;
        this.location = location;
        this.server = server;
        this.usn = usn;
        this.st = st;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUSN() {
        return this.usn;
    }

    public void setUSN(String usn) {
        this.usn = usn;
    }

    public String getST() {
        return this.st;
    }

    public void setST(String st) {
        this.st = st;
    }

    public UPnPDeviceInformation getDeviceInformation() {
        return this.deviceInformation;
    }

    public void setDeviceInformation(UPnPDeviceInformation deviceInformation) {
        this.deviceInformation = deviceInformation;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.UPNP;
    }
}

