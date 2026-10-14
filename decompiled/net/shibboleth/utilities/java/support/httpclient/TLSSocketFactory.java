/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.annotation.ThreadSafe
 */
package net.shibboleth.utilities.java.support.httpclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.http.HttpHost;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class TLSSocketFactory
implements LayeredConnectionSocketFactory {
    public static final String CONTEXT_KEY_TLS_PROTOCOLS = "javasupport.TLSProtocols";
    public static final String CONTEXT_KEY_TLS_CIPHER_SUITES = "javasupport.TLSCipherSuites";
    public static final String CONTEXT_KEY_HOSTNAME_VERIFIER = "javasupport.HostnameVerifier";
    public static final String TLS = "TLS";
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    private final Logger log = LoggerFactory.getLogger(TLSSocketFactory.class);
    private final SSLSocketFactory socketfactory;
    private final X509HostnameVerifier hostnameVerifier;
    private final String[] supportedProtocols;
    private final String[] supportedCipherSuites;

    public TLSSocketFactory(@Nonnull SSLContext sslContext) {
        this(sslContext, STRICT_HOSTNAME_VERIFIER);
    }

    public TLSSocketFactory(@Nonnull SSLContext sslContext, @Nullable X509HostnameVerifier verifier) {
        this(Args.notNull(sslContext, "SSL context").getSocketFactory(), null, null, verifier);
    }

    public TLSSocketFactory(@Nonnull SSLContext sslContext, @Nullable String[] protocols, @Nullable String[] cipherSuites, @Nullable X509HostnameVerifier verifier) {
        this(Args.notNull(sslContext, "SSL context").getSocketFactory(), protocols, cipherSuites, verifier);
    }

    public TLSSocketFactory(@Nonnull SSLSocketFactory factory2, @Nullable X509HostnameVerifier verifier) {
        this(factory2, null, null, verifier);
    }

    public TLSSocketFactory(@Nonnull SSLSocketFactory factory2, @Nullable String[] protocols, @Nullable String[] cipherSuites, @Nullable X509HostnameVerifier verifier) {
        this.socketfactory = Args.notNull(factory2, "SSL socket factory");
        this.supportedProtocols = protocols;
        this.supportedCipherSuites = cipherSuites;
        this.hostnameVerifier = verifier != null ? verifier : STRICT_HOSTNAME_VERIFIER;
    }

    @Nonnull
    protected SSLSocketFactory getSocketfactory() {
        return this.socketfactory;
    }

    @Nonnull
    protected X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Nullable
    protected String[] getSupportedProtocols() {
        return this.supportedProtocols;
    }

    @Nullable
    protected String[] getSupportedCipherSuites() {
        return this.supportedCipherSuites;
    }

    protected void prepareSocket(@Nonnull SSLSocket socket, @Nullable HttpContext context) throws IOException {
    }

    @Override
    @Nonnull
    public Socket createSocket(@Nullable HttpContext context) throws IOException {
        this.log.trace("In createSocket");
        return SocketFactory.getDefault().createSocket();
    }

    @Override
    public Socket connectSocket(int connectTimeout, @Nullable Socket socket, @Nonnull HttpHost host2, @Nonnull InetSocketAddress remoteAddress, @Nullable InetSocketAddress localAddress, @Nullable HttpContext context) throws IOException {
        Socket sock;
        this.log.trace("In connectSocket");
        Args.notNull(host2, "HTTP host");
        Args.notNull(remoteAddress, "Remote address");
        Socket socket2 = sock = socket != null ? socket : this.createSocket(context);
        if (localAddress != null) {
            sock.bind(localAddress);
        }
        try {
            if (connectTimeout > 0 && sock.getSoTimeout() == 0) {
                sock.setSoTimeout(connectTimeout);
            }
            sock.connect(remoteAddress, connectTimeout);
        }
        catch (IOException ex) {
            try {
                sock.close();
            }
            catch (IOException ignore) {
                // empty catch block
            }
            throw ex;
        }
        if (sock instanceof SSLSocket) {
            SSLSocket sslsock = (SSLSocket)sock;
            sslsock.startHandshake();
            this.verifyHostname(sslsock, host2.getHostName(), context);
            return sock;
        }
        return this.createLayeredSocket(sock, host2.getHostName(), remoteAddress.getPort(), context);
    }

    @Override
    public Socket createLayeredSocket(@Nonnull Socket socket, @Nonnull @NotEmpty String target, int port, @Nullable HttpContext context) throws IOException {
        this.log.trace("In createLayeredSocket");
        SSLSocket sslsock = (SSLSocket)this.getSocketfactory().createSocket(socket, target, port, true);
        String[] contextProtocols = this.getListAttribute(context, CONTEXT_KEY_TLS_PROTOCOLS);
        if (contextProtocols != null) {
            sslsock.setEnabledProtocols(contextProtocols);
        } else if (this.getSupportedProtocols() != null) {
            sslsock.setEnabledProtocols(this.getSupportedProtocols());
        } else {
            String[] allProtocols = sslsock.getSupportedProtocols();
            ArrayList<String> enabledProtocols = new ArrayList<String>(allProtocols.length);
            for (String protocol : allProtocols) {
                if (protocol.startsWith(SSL)) continue;
                enabledProtocols.add(protocol);
            }
            sslsock.setEnabledProtocols(enabledProtocols.toArray(new String[enabledProtocols.size()]));
        }
        String[] contextCipherSuites = this.getListAttribute(context, CONTEXT_KEY_TLS_CIPHER_SUITES);
        if (contextCipherSuites != null) {
            sslsock.setEnabledCipherSuites(contextCipherSuites);
        } else if (this.getSupportedCipherSuites() != null) {
            sslsock.setEnabledCipherSuites(this.getSupportedCipherSuites());
        }
        this.prepareSocket(sslsock, context);
        sslsock.startHandshake();
        this.logSocketInfo(sslsock);
        this.verifyHostname(sslsock, target, context);
        return sslsock;
    }

    private void logSocketInfo(SSLSocket socket) {
        SSLSession session = socket.getSession();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connected to: {}", (Object)socket.getRemoteSocketAddress());
            this.log.debug("Supported protocols: {}", (Object)socket.getSupportedProtocols());
            this.log.debug("Enabled protocols:   {}", (Object)socket.getEnabledProtocols());
            this.log.debug("Selected protocol:   {}", (Object)session.getProtocol());
            this.log.debug("Supported cipher suites: {}", (Object)socket.getSupportedCipherSuites());
            this.log.debug("Enabled cipher suites:   {}", (Object)socket.getEnabledCipherSuites());
            this.log.debug("Selected cipher suite:   {}", (Object)session.getCipherSuite());
        }
        if (this.log.isTraceEnabled()) {
            try {
                this.log.trace("Peer principal: {}", (Object)session.getPeerPrincipal());
                this.log.trace("Peer certificates: {}", (Object)session.getPeerCertificates());
                this.log.trace("Local principal: {}", (Object)session.getLocalPrincipal());
                this.log.trace("Local certificates: {}", (Object)session.getLocalCertificates());
            }
            catch (SSLPeerUnverifiedException e) {
                this.log.warn("SSL exception enumerating peer certificates", e);
            }
        }
    }

    @Nullable
    protected String[] getListAttribute(@Nullable HttpContext context, @Nonnull String contextKey) {
        if (context == null) {
            return null;
        }
        ArrayList<String> values2 = new ArrayList<String>(StringSupport.normalizeStringCollection((List)context.getAttribute(contextKey)));
        if (values2 != null && !values2.isEmpty()) {
            return values2.toArray(new String[values2.size()]);
        }
        return null;
    }

    protected void verifyHostname(@Nonnull SSLSocket sslsock, @Nonnull String hostname, @Nullable HttpContext context) throws IOException {
        try {
            X509HostnameVerifier verifier = null;
            if (context != null) {
                verifier = (X509HostnameVerifier)context.getAttribute(CONTEXT_KEY_HOSTNAME_VERIFIER);
            }
            if (verifier == null) {
                verifier = this.getHostnameVerifier();
            }
            verifier.verify(hostname, sslsock);
        }
        catch (IOException iox) {
            try {
                sslsock.close();
            }
            catch (Exception x) {
                // empty catch block
            }
            throw iox;
        }
    }
}

