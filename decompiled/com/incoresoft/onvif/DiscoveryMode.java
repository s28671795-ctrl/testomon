/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

public enum DiscoveryMode {
    ONVIF(3702),
    UPNP(1900);

    public final int port;

    private DiscoveryMode(int port) {
        this.port = port;
    }
}

