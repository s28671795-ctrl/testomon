/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.cxf.common.logging.LogUtils;

class SSLSocketFactoryWrapper
extends SSLSocketFactory {
    private static final Logger LOG = LogUtils.getL7dLogger(SSLSocketFactoryWrapper.class);
    private SSLSocketFactory sslSocketFactory;
    private String[] ciphers;
    private String protocol;

    SSLSocketFactoryWrapper(SSLSocketFactory sslSocketFactoryParam, String[] ciphersParam, String protocolParam) {
        this.sslSocketFactory = sslSocketFactoryParam;
        this.ciphers = ciphersParam;
        this.protocol = protocolParam;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return this.sslSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.sslSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s2, String host2, int port, boolean autoClose) throws IOException, UnknownHostException {
        return this.enableCipherSuites(this.sslSocketFactory.createSocket(s2, host2, port, autoClose), new Object[]{host2, port});
    }

    @Override
    public Socket createSocket(String host2, int port) throws IOException, UnknownHostException {
        return this.enableCipherSuites(this.sslSocketFactory.createSocket(host2, port), new Object[]{host2, port});
    }

    @Override
    public Socket createSocket(String host2, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return this.enableCipherSuites(this.sslSocketFactory.createSocket(host2, port, localHost, localPort), new Object[]{host2, port});
    }

    @Override
    public Socket createSocket(InetAddress host2, int port) throws IOException {
        return this.enableCipherSuites(this.sslSocketFactory.createSocket(host2, port), new Object[]{host2, port});
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return this.enableCipherSuites(this.sslSocketFactory.createSocket(address, port, localAddress, localPort), new Object[]{address, port});
    }

    private Socket enableCipherSuites(Socket s2, Object[] logParams) {
        String[] p;
        SSLSocket socket = (SSLSocket)s2;
        if (socket == null) {
            LogUtils.log(LOG, Level.SEVERE, "PROBLEM_CREATING_OUTBOUND_REQUEST_SOCKET", logParams);
            return socket;
        }
        if (this.protocol != null && (p = this.findProtocols(this.protocol, socket.getSupportedProtocols())) != null) {
            socket.setEnabledProtocols(p);
        }
        if (this.ciphers != null) {
            socket.setEnabledCipherSuites(this.ciphers);
        }
        return socket;
    }

    private String[] findProtocols(String p, String[] options) {
        ArrayList<String> list = new ArrayList<String>();
        for (String s2 : options) {
            if (s2.equals(p)) {
                return new String[]{p};
            }
            if (!s2.startsWith(p)) continue;
            list.add(s2);
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[0]);
    }

    protected void addLogHandler(Handler handler) {
        LOG.addHandler(handler);
    }
}

