/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

import com.incoresoft.onvif.models.Device;
import com.incoresoft.onvif.models.DeviceType;
import com.incoresoft.onvif.models.OnvifServices;
import java.util.ArrayList;
import java.util.List;

public class OnvifDevice
extends Device {
    public static final String TAG = OnvifDevice.class.getSimpleName();
    private OnvifServices path = new OnvifServices();
    private List<String> addresses = new ArrayList<String>();

    public OnvifDevice(String hostName) {
        this(hostName, "", "");
    }

    public OnvifDevice(String hostName, String username, String password) {
        super(hostName, username, password);
    }

    public void addAddress(String address) {
        this.addresses.add(address);
    }

    public OnvifServices getPath() {
        return this.path;
    }

    public void setPath(OnvifServices path) {
        this.path = path;
    }

    public List<String> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.ONVIF;
    }
}

