/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.IOException;
import java.net.BindException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.security.CipherFactory;
import org.h2.util.StringUtils;

public class NetUtils {
    private static final int CACHE_MILLIS = 1000;
    private static InetAddress cachedBindAddress;
    private static String cachedLocalAddress;
    private static long cachedLocalAddressTime;

    private NetUtils() {
    }

    public static Socket createLoopbackSocket(int n, boolean bl) throws IOException {
        String string = NetUtils.getLocalAddress();
        try {
            return NetUtils.createSocket(string, n, bl);
        }
        catch (IOException iOException) {
            try {
                return NetUtils.createSocket("localhost", n, bl);
            }
            catch (IOException iOException2) {
                throw iOException;
            }
        }
    }

    public static Socket createSocket(String string, int n, boolean bl) throws IOException {
        return NetUtils.createSocket(string, n, bl, 0);
    }

    public static Socket createSocket(String string, int n, boolean bl, int n2) throws IOException {
        int n3 = n;
        int n4 = string.startsWith("[") ? string.indexOf(93) : 0;
        int n5 = string.indexOf(58, n4);
        if (n5 >= 0) {
            n3 = Integer.decode(string.substring(n5 + 1));
            string = string.substring(0, n5);
        }
        InetAddress inetAddress = InetAddress.getByName(string);
        return NetUtils.createSocket(inetAddress, n3, bl, n2);
    }

    public static Socket createSocket(InetAddress inetAddress, int n, boolean bl) throws IOException {
        return NetUtils.createSocket(inetAddress, n, bl, 0);
    }

    public static Socket createSocket(InetAddress inetAddress, int n, boolean bl, int n2) throws IOException {
        long l = System.nanoTime();
        int n3 = 0;
        while (true) {
            try {
                if (bl) {
                    return CipherFactory.createSocket(inetAddress, n);
                }
                Socket socket = new Socket();
                socket.setSoTimeout(n2);
                socket.connect(new InetSocketAddress(inetAddress, n), SysProperties.SOCKET_CONNECT_TIMEOUT);
                return socket;
            }
            catch (IOException iOException) {
                if (System.nanoTime() - l >= (long)SysProperties.SOCKET_CONNECT_TIMEOUT * 1000000L) {
                    throw iOException;
                }
                if (n3 >= SysProperties.SOCKET_CONNECT_RETRY) {
                    throw iOException;
                }
                try {
                    long l2 = Math.min(256, n3 * n3);
                    Thread.sleep(l2);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                ++n3;
                continue;
            }
            break;
        }
    }

    public static ServerSocket createServerSocket(int n, boolean bl) {
        try {
            return NetUtils.createServerSocketTry(n, bl);
        }
        catch (Exception exception) {
            return NetUtils.createServerSocketTry(n, bl);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static InetAddress getBindAddress() throws UnknownHostException {
        String string = SysProperties.BIND_ADDRESS;
        if (string == null || string.isEmpty()) {
            return null;
        }
        Class<NetUtils> clazz = NetUtils.class;
        synchronized (NetUtils.class) {
            if (cachedBindAddress == null) {
                cachedBindAddress = InetAddress.getByName(string);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return cachedBindAddress;
        }
    }

    private static ServerSocket createServerSocketTry(int n, boolean bl) {
        try {
            InetAddress inetAddress = NetUtils.getBindAddress();
            if (bl) {
                return CipherFactory.createServerSocket(n, inetAddress);
            }
            if (inetAddress == null) {
                return new ServerSocket(n);
            }
            return new ServerSocket(n, 0, inetAddress);
        }
        catch (BindException bindException) {
            throw DbException.get(90061, bindException, Integer.toString(n), bindException.toString());
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, "port: " + n + " ssl: " + bl);
        }
    }

    public static boolean isLocalAddress(Socket socket) throws UnknownHostException {
        InetAddress inetAddress = socket.getInetAddress();
        if (inetAddress.isLoopbackAddress()) {
            return true;
        }
        InetAddress inetAddress2 = InetAddress.getLocalHost();
        String string = inetAddress2.getHostAddress();
        for (InetAddress inetAddress3 : InetAddress.getAllByName(string)) {
            if (!inetAddress.equals(inetAddress3)) continue;
            return true;
        }
        return false;
    }

    public static ServerSocket closeSilently(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    public static synchronized String getLocalAddress() {
        String string;
        long l = System.nanoTime();
        if (cachedLocalAddress != null && l - cachedLocalAddressTime < 1000000000L) {
            return cachedLocalAddress;
        }
        InetAddress inetAddress = null;
        boolean bl = false;
        try {
            inetAddress = NetUtils.getBindAddress();
            if (inetAddress == null) {
                bl = true;
            }
        }
        catch (UnknownHostException unknownHostException) {
            // empty catch block
        }
        if (bl) {
            try {
                inetAddress = InetAddress.getLocalHost();
            }
            catch (UnknownHostException unknownHostException) {
                throw DbException.convert(unknownHostException);
            }
        }
        if (inetAddress == null) {
            string = "localhost";
        } else {
            string = inetAddress.getHostAddress();
            if (inetAddress instanceof Inet6Address) {
                if (string.indexOf(37) >= 0) {
                    string = "localhost";
                } else if (string.indexOf(58) >= 0 && !string.startsWith("[")) {
                    string = "[" + string + "]";
                }
            }
        }
        if (string.equals("127.0.0.1")) {
            string = "localhost";
        }
        cachedLocalAddress = string;
        cachedLocalAddressTime = l;
        return string;
    }

    public static String getHostName(String string) {
        try {
            InetAddress inetAddress = InetAddress.getByName(string);
            return inetAddress.getHostName();
        }
        catch (Exception exception) {
            return "unknown";
        }
    }

    public static StringBuilder ipToShortForm(StringBuilder stringBuilder, byte[] byArray, boolean bl) {
        switch (byArray.length) {
            case 4: {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder(15);
                }
                stringBuilder.append(byArray[0] & 0xFF).append('.').append(byArray[1] & 0xFF).append('.').append(byArray[2] & 0xFF).append('.').append(byArray[3] & 0xFF);
                break;
            }
            case 16: {
                int n;
                short[] sArray = new short[8];
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                for (n = 0; n < 8; ++n) {
                    if ((sArray[n] = (short)((byArray[n5++] & 0xFF) << 8 | byArray[n5++] & 0xFF)) == 0) {
                        if (++n4 <= n3) continue;
                        n3 = n4;
                        n2 = n - n4 + 1;
                        continue;
                    }
                    n4 = 0;
                }
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder(bl ? 41 : 39);
                }
                if (bl) {
                    stringBuilder.append('[');
                }
                if (n3 > 1) {
                    for (n5 = 0; n5 < n2; ++n5) {
                        stringBuilder.append(Integer.toHexString(sArray[n5] & 0xFFFF)).append(':');
                    }
                    if (n2 == 0) {
                        stringBuilder.append(':');
                    }
                    stringBuilder.append(':');
                    n = n2 + n3;
                } else {
                    n = 0;
                }
                for (n5 = n; n5 < 8; ++n5) {
                    stringBuilder.append(Integer.toHexString(sArray[n5] & 0xFFFF));
                    if (n5 >= 7) continue;
                    stringBuilder.append(':');
                }
                if (!bl) break;
                stringBuilder.append(']');
                break;
            }
            default: {
                StringUtils.convertBytesToHex(stringBuilder, byArray);
            }
        }
        return stringBuilder;
    }
}

