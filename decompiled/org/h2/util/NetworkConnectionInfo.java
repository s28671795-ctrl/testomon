/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.h2.util.NetUtils;

public final class NetworkConnectionInfo {
    private final String server;
    private final byte[] clientAddr;
    private final int clientPort;
    private final String clientInfo;

    public NetworkConnectionInfo(String string, String string2, int n) throws UnknownHostException {
        this(string, InetAddress.getByName(string2).getAddress(), n, null);
    }

    public NetworkConnectionInfo(String string, byte[] byArray, int n, String string2) {
        this.server = string;
        this.clientAddr = byArray;
        this.clientPort = n;
        this.clientInfo = string2;
    }

    public String getServer() {
        return this.server;
    }

    public byte[] getClientAddr() {
        return this.clientAddr;
    }

    public int getClientPort() {
        return this.clientPort;
    }

    public String getClientInfo() {
        return this.clientInfo;
    }

    public String getClient() {
        return NetUtils.ipToShortForm(new StringBuilder(), this.clientAddr, true).append(':').append(this.clientPort).toString();
    }
}

