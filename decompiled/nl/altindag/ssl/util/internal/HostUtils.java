/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util.internal;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Map;
import javax.net.ssl.SSLEngine;

public final class HostUtils {
    private HostUtils() {
    }

    public static Map.Entry<String, Integer> extractHostAndPort(Socket socket) {
        InetSocketAddress address = (InetSocketAddress)socket.getRemoteSocketAddress();
        return new AbstractMap.SimpleImmutableEntry<String, Integer>(address.getHostName(), address.getPort());
    }

    public static Map.Entry<String, Integer> extractHostAndPort(SSLEngine sslEngine) {
        return new AbstractMap.SimpleImmutableEntry<String, Integer>(sslEngine.getPeerHost(), sslEngine.getPeerPort());
    }
}

