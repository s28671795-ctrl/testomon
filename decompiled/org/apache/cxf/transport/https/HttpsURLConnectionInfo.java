/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;
import org.apache.cxf.transport.http.HttpURLConnectionInfo;

public class HttpsURLConnectionInfo
extends HttpURLConnectionInfo {
    protected String enabledCipherSuite;
    protected Certificate[] localCertificates;
    protected Principal localPrincipal;
    protected Certificate[] serverCertificates;
    protected Principal peerPrincipal;

    public HttpsURLConnectionInfo(URI uri, String method2, String cipherSuite, Certificate[] localCerts, Principal principal, Certificate[] serverCerts, Principal peer) {
        super(uri, method2);
        this.enabledCipherSuite = cipherSuite;
        this.localCertificates = localCerts;
        this.localPrincipal = principal;
        this.serverCertificates = serverCerts;
        this.peerPrincipal = peer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public HttpsURLConnectionInfo(HttpURLConnection connection) throws IOException {
        block8: {
            super(connection.getURL(), connection.getRequestMethod());
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection conn = (HttpsURLConnection)connection;
                this.enabledCipherSuite = conn.getCipherSuite();
                this.localCertificates = conn.getLocalCertificates();
                this.localPrincipal = conn.getLocalPrincipal();
                this.serverCertificates = conn.getServerCertificates();
                this.peerPrincipal = conn.getPeerPrincipal();
            } else {
                Exception ex = null;
                try {
                    Method method2 = null;
                    method2 = connection.getClass().getMethod("getCipherSuite", null);
                    this.enabledCipherSuite = (String)method2.invoke((Object)connection, (Object[])null);
                    method2 = connection.getClass().getMethod("getLocalCertificates", null);
                    this.localCertificates = (Certificate[])method2.invoke((Object)connection, (Object[])null);
                    method2 = connection.getClass().getMethod("getServerCertificates", null);
                    this.serverCertificates = (Certificate[])method2.invoke((Object)connection, (Object[])null);
                }
                catch (Exception e) {
                    ex = e;
                    return ex;
                }
                finally {
                    if (ex == null) break block8;
                    if (ex instanceof IOException) {
                        throw (IOException)ex;
                    }
                    IOException ioe = new IOException("Error constructing HttpsURLConnectionInfo for connection class " + connection.getClass().getName());
                    ioe.initCause(ex);
                    throw ioe;
                }
            }
        }
    }

    public String getEnabledCipherSuite() {
        return this.enabledCipherSuite;
    }

    public Certificate[] getLocalCertificates() {
        return this.localCertificates;
    }

    public Principal getLocalPrincipal() {
        return this.localPrincipal;
    }

    public Certificate[] getServerCertificates() {
        return this.serverCertificates;
    }

    public Principal getPeerPrincipal() {
        return this.peerPrincipal;
    }
}

