/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import liquibase.Scope;

public class NetUtil {
    private static InetAddress localHost;
    private static String hostName;
    private static final String UNKNOWN_HOST_NAME = "unknown";

    private NetUtil() {
    }

    private static InetAddress getLocalHost() throws UnknownHostException, SocketException {
        if (localHost == null) {
            InetAddress foundHost = InetAddress.getLocalHost();
            if (foundHost == null || foundHost.isLoopbackAddress() || foundHost.isLinkLocalAddress()) {
                Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
                while (e.hasMoreElements()) {
                    NetworkInterface i = e.nextElement();
                    if (!i.isUp() || i.isPointToPoint()) continue;
                    Enumeration<InetAddress> ie = i.getInetAddresses();
                    while (ie.hasMoreElements()) {
                        InetAddress lch = ie.nextElement();
                        if (lch.isLoopbackAddress() || lch.isLinkLocalAddress()) continue;
                        localHost = lch;
                        return localHost;
                    }
                }
            }
            localHost = foundHost;
        }
        return localHost;
    }

    public static String getLocalHostAddress() {
        try {
            InetAddress localHost = NetUtil.getLocalHost();
            if (localHost != null) {
                return localHost.getHostAddress();
            }
            return UNKNOWN_HOST_NAME;
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(NetUtil.class).fine("Error getting hostname", e);
            return UNKNOWN_HOST_NAME;
        }
    }

    public static String getLocalHostName() {
        block6: {
            if (hostName == null) {
                try {
                    InetAddress localHost = NetUtil.getLocalHost();
                    if (localHost != null) {
                        InetAddress lHost;
                        hostName = localHost.getHostName();
                        if (hostName.equals(localHost.getHostAddress()) && (lHost = InetAddress.getLocalHost()) != null) {
                            hostName = lHost.getHostName();
                        }
                    } else {
                        hostName = UNKNOWN_HOST_NAME;
                    }
                }
                catch (Exception e) {
                    Scope.getCurrentScope().getLog(NetUtil.class).fine("Error getting hostname", e);
                    if (hostName != null) break block6;
                    hostName = UNKNOWN_HOST_NAME;
                }
            }
        }
        return hostName;
    }
}

