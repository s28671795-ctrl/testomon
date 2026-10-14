/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.httpclient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.shibboleth.utilities.java.support.httpclient.TLSSocketFactoryBuilder;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.StrictHostnameVerifier;

public final class HttpClientSupport {
    private HttpClientSupport() {
    }

    public static LayeredConnectionSocketFactory buildStrictTLSSocketFactory() {
        return new TLSSocketFactoryBuilder().setHostnameVerifier(new StrictHostnameVerifier()).build();
    }

    public static LayeredConnectionSocketFactory buildNoTrustTLSSocketFactory() {
        return new TLSSocketFactoryBuilder().setTrustManagers(Collections.singletonList(HttpClientSupport.buildNoTrustX509TrustManager())).setHostnameVerifier(new AllowAllHostnameVerifier()).build();
    }

    @Deprecated
    public static SSLConnectionSocketFactory buildStrictSSLConnectionSocketFactory() {
        return new SSLConnectionSocketFactory(SSLContexts.createDefault(), SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
    }

    @Deprecated
    public static SSLConnectionSocketFactory buildNoTrustSSLConnectionSocketFactory() {
        X509TrustManager noTrustManager = HttpClientSupport.buildNoTrustX509TrustManager();
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{noTrustManager}, null);
            return new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("TLS SSLContext type is required to be supported by the JVM but is not", e);
        }
        catch (KeyManagementException e) {
            throw new RuntimeException("Some how the trust everything trust manager didn't trust everything", e);
        }
    }

    public static X509TrustManager buildNoTrustX509TrustManager() {
        return new X509TrustManager(){

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
    }
}

