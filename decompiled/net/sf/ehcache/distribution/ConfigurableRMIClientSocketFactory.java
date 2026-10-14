/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMISocketFactory;

public final class ConfigurableRMIClientSocketFactory
implements Serializable,
RMIClientSocketFactory {
    private static final int ONE_SECOND = 1000;
    private static final long serialVersionUID = 4920508630517373246L;
    private final int socketTimeoutMillis;

    public ConfigurableRMIClientSocketFactory(Integer socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis == null ? 1000 : socketTimeoutMillis;
    }

    @Override
    public Socket createSocket(String host2, int port) throws IOException {
        Socket socket = ConfigurableRMIClientSocketFactory.getConfiguredRMISocketFactory().createSocket(host2, port);
        socket.setSoTimeout(this.socketTimeoutMillis);
        return socket;
    }

    public int hashCode() {
        return this.socketTimeoutMillis;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        return this.getClass() == object.getClass() && this.socketTimeoutMillis == ((ConfigurableRMIClientSocketFactory)object).socketTimeoutMillis;
    }

    public static RMISocketFactory getConfiguredRMISocketFactory() {
        RMISocketFactory globalSocketFactory = RMISocketFactory.getSocketFactory();
        if (globalSocketFactory == null) {
            return RMISocketFactory.getDefaultSocketFactory();
        }
        return globalSocketFactory;
    }
}

