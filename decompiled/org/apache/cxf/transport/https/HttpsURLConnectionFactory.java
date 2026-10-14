/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.util.logging.Handler;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionInvokationHandler;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.transport.https.SSLSocketFactoryWrapper;
import org.apache.cxf.transport.https.SSLUtils;

public class HttpsURLConnectionFactory {
    public static final String HTTPS_URL_PROTOCOL_ID = "https";
    private static final Logger LOG = LogUtils.getL7dLogger(HttpsURLConnectionFactory.class);
    private static boolean weblogicWarned;
    SSLSocketFactory socketFactory;
    int lastTlsHash;

    public HttpURLConnection createConnection(TLSClientParameters tlsClientParameters, java.net.Proxy proxy, URL url2) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)(proxy != null ? url2.openConnection(proxy) : url2.openConnection());
        if (HTTPS_URL_PROTOCOL_ID.equals(url2.getProtocol())) {
            if (tlsClientParameters == null) {
                tlsClientParameters = new TLSClientParameters();
            }
            try {
                this.decorateWithTLS(tlsClientParameters, connection);
            }
            catch (Throwable ex) {
                throw new IOException("Error while initializing secure socket", ex);
            }
        }
        return connection;
    }

    protected synchronized void decorateWithTLS(TLSClientParameters tlsClientParameters, HttpURLConnection connection) throws GeneralSecurityException {
        int hash = tlsClientParameters.hashCode();
        if (hash != this.lastTlsHash) {
            this.lastTlsHash = hash;
            this.socketFactory = null;
        }
        if (tlsClientParameters.isUseHttpsURLConnectionDefaultSslSocketFactory()) {
            this.socketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        } else if (tlsClientParameters.getSSLSocketFactory() != null) {
            this.socketFactory = tlsClientParameters.getSSLSocketFactory();
        } else if (this.socketFactory == null) {
            SSLContext ctx = null;
            ctx = tlsClientParameters.getSslContext() != null ? tlsClientParameters.getSslContext() : SSLUtils.getSSLContext(tlsClientParameters);
            String[] cipherSuites = org.apache.cxf.configuration.jsse.SSLUtils.getCiphersuitesToInclude(tlsClientParameters.getCipherSuites(), tlsClientParameters.getCipherSuitesFilter(), ctx.getSocketFactory().getDefaultCipherSuites(), org.apache.cxf.configuration.jsse.SSLUtils.getSupportedCipherSuites(ctx), LOG);
            String protocol = tlsClientParameters.getSecureSocketProtocol() != null ? tlsClientParameters.getSecureSocketProtocol() : ctx.getProtocol();
            this.socketFactory = new SSLSocketFactoryWrapper(ctx.getSocketFactory(), cipherSuites, protocol);
            this.lastTlsHash = tlsClientParameters.hashCode();
        }
        HostnameVerifier verifier = SSLUtils.getHostnameVerifier(tlsClientParameters);
        if (connection instanceof HttpsURLConnection) {
            final HttpsURLConnection conn = (HttpsURLConnection)connection;
            conn.setHostnameVerifier(verifier);
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    conn.setSSLSocketFactory(HttpsURLConnectionFactory.this.socketFactory);
                    return null;
                }
            });
        } else {
            try {
                Method method2 = connection.getClass().getMethod("getHostnameVerifier", new Class[0]);
                ReflectionInvokationHandler handler = new ReflectionInvokationHandler(verifier){

                    @Override
                    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
                        try {
                            return super.invoke(proxy, method2, args2);
                        }
                        catch (Exception ex) {
                            return false;
                        }
                    }
                };
                Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{method2.getReturnType()}, handler);
                method2 = connection.getClass().getMethod("setHostnameVerifier", method2.getReturnType());
                method2.invoke((Object)connection, proxy);
            }
            catch (Exception method2) {
                // empty catch block
            }
            try {
                Method getSSLSocketFactory = connection.getClass().getMethod("getSSLSocketFactory", new Class[0]);
                Method setSSLSocketFactory = connection.getClass().getMethod("setSSLSocketFactory", getSSLSocketFactory.getReturnType());
                if (getSSLSocketFactory.getReturnType().isInstance(this.socketFactory)) {
                    setSSLSocketFactory.invoke((Object)connection, this.socketFactory);
                } else {
                    Constructor<?> c = getSSLSocketFactory.getReturnType().getDeclaredConstructor(SSLSocketFactory.class);
                    ReflectionUtil.setAccessible(c);
                    setSSLSocketFactory.invoke((Object)connection, c.newInstance(this.socketFactory));
                }
            }
            catch (Exception ex) {
                if (connection.getClass().getName().contains("weblogic")) {
                    if (!weblogicWarned) {
                        weblogicWarned = true;
                        LOG.warning("Could not configure SSLSocketFactory on Weblogic.   Use the Weblogic control panel to configure the SSL settings.");
                    }
                    return;
                }
                throw new IllegalArgumentException("Error decorating connection class " + connection.getClass().getName(), ex);
            }
        }
    }

    protected void addLogHandler(Handler handler) {
        LOG.addHandler(handler);
    }
}

