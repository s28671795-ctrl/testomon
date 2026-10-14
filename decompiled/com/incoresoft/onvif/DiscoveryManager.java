/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

import com.incoresoft.onvif.DiscoveryMode;
import com.incoresoft.onvif.OnvifDiscovery;
import com.incoresoft.onvif.listeners.DiscoveryListener;
import java.net.InetAddress;
import java.util.List;

public class DiscoveryManager {
    public static final String TAG = DiscoveryManager.class.getSimpleName();
    private final OnvifDiscovery discovery = new OnvifDiscovery();

    public void discover(DiscoveryListener discoveryListener) {
        this.discover(DiscoveryMode.ONVIF, discoveryListener);
    }

    public void discover(DiscoveryMode mode, DiscoveryListener discoveryListener) {
        this.discovery.probe(mode, discoveryListener);
    }

    public List<InetAddress> getInterfaceAddresses() {
        return this.discovery.getInterfaceAddresses();
    }

    public List<InetAddress> getBroadcastAddresses() {
        return this.discovery.getBroadcastAddresses();
    }

    public String getLocalIpAddress() {
        return this.discovery.getLocalIpAddress();
    }

    public int getDiscoveryTimeout() {
        return this.discovery.getDiscoveryTimeout();
    }

    public void setDiscoveryTimeout(int timeoutMs) {
        this.discovery.setDiscoveryTimeout(timeoutMs);
    }

    public DiscoveryMode getDiscoveryMode() {
        return this.discovery.getDiscoveryMode();
    }

    public void setDiscoveryMode(DiscoveryMode mode) {
        this.discovery.setDiscoveryMode(mode);
    }
}

