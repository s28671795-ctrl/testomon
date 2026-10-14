/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.wireformat.WireFormat;

public class SslTransport
extends TcpTransport {
    private Boolean verifyHostName = null;

    public SslTransport(WireFormat wireFormat, SSLSocketFactory socketFactory, URI remoteLocation, URI localLocation, boolean needClientAuth) throws IOException {
        super(wireFormat, socketFactory, remoteLocation, localLocation);
        if (this.socket != null) {
            ((SSLSocket)this.socket).setNeedClientAuth(needClientAuth);
            HashMap<String, String> props = new HashMap<String, String>();
            props.put("host", remoteLocation.getHost());
            IntrospectionSupport.setProperties(this.socket, props);
        }
    }

    @Override
    protected void initialiseSocket(Socket sock) throws SocketException, IllegalArgumentException {
        if (this.verifyHostName == null) {
            if (this.socketOptions != null && this.socketOptions.containsKey("verifyHostName")) {
                this.verifyHostName = Boolean.parseBoolean(this.socketOptions.get("verifyHostName").toString());
                this.socketOptions.remove("verifyHostName");
            } else {
                this.verifyHostName = true;
            }
        }
        if (this.verifyHostName.booleanValue()) {
            SSLParameters sslParams = new SSLParameters();
            sslParams.setEndpointIdentificationAlgorithm("HTTPS");
            ((SSLSocket)this.socket).setSSLParameters(sslParams);
        }
        super.initialiseSocket(sock);
    }

    public SslTransport(WireFormat wireFormat, SSLSocket socket) throws IOException {
        super(wireFormat, socket);
    }

    public SslTransport(WireFormat format, SSLSocket socket, TcpTransport.InitBuffer initBuffer) throws IOException {
        super(format, socket, initBuffer);
    }

    @Override
    public void doConsume(Object command) {
        if (command instanceof ConnectionInfo) {
            ConnectionInfo connectionInfo = (ConnectionInfo)command;
            connectionInfo.setTransportContext(this.getPeerCertificates());
        }
        super.doConsume(command);
    }

    public void setVerifyHostName(Boolean verifyHostName) {
        this.verifyHostName = verifyHostName;
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        X509Certificate[] clientCertChain;
        SSLSocket sslSocket = (SSLSocket)this.socket;
        SSLSession sslSession = sslSocket.getSession();
        try {
            clientCertChain = (X509Certificate[])sslSession.getPeerCertificates();
        }
        catch (SSLPeerUnverifiedException e) {
            clientCertChain = null;
        }
        return clientCertChain;
    }

    @Override
    public String toString() {
        return "ssl://" + this.socket.getInetAddress() + ":" + this.socket.getPort();
    }
}

