/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.BindingProvider;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.transport.http.HTTPConduit;

public class NaiveSSLHelper {
    private static final String JAXWS_HOSTNAME_VERIFIER = "com.sun.xml.internal.ws.transport.https.client.hostname.verifier";
    private static final String JAXWS_SSL_SOCKET_FACTORY = "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory";

    public static void makeWebServiceClientTrustEveryone(Object webServicePort) {
        if (!(webServicePort instanceof BindingProvider)) {
            throw new IllegalArgumentException("Web service port " + webServicePort.getClass().getName() + " does not implement " + BindingProvider.class.getName());
        }
        BindingProvider bp = (BindingProvider)webServicePort;
        Map<String, Object> requestContext = bp.getRequestContext();
        requestContext.put(JAXWS_SSL_SOCKET_FACTORY, NaiveSSLHelper.getTrustingSSLSocketFactory());
        requestContext.put(JAXWS_HOSTNAME_VERIFIER, new NaiveHostnameVerifier());
    }

    public static SSLSocketFactory getTrustingSSLSocketFactory() {
        return SSLSocketFactoryHolder.INSTANCE;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        TrustManager[] trustManagers = new TrustManager[]{new NaiveTrustManager()};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        }
        catch (GeneralSecurityException e) {
            return null;
        }
    }

    public static void makeCxfWebServiceClientTrustEveryone(HTTPConduit http) {
        TrustManager[] trustManagers = new TrustManager[]{new NaiveTrustManager()};
        TLSClientParameters tlsParams = new TLSClientParameters();
        tlsParams.setSecureSocketProtocol("TLS");
        tlsParams.setKeyManagers(new KeyManager[0]);
        tlsParams.setTrustManagers(trustManagers);
        tlsParams.setDisableCNCheck(true);
        http.setTlsClientParameters(tlsParams);
    }

    static /* synthetic */ SSLSocketFactory access$200() {
        return NaiveSSLHelper.createSSLSocketFactory();
    }

    private static class NaiveHostnameVerifier
    implements HostnameVerifier {
        private NaiveHostnameVerifier() {
        }

        @Override
        public boolean verify(String hostName, SSLSession session) {
            return true;
        }
    }

    private static interface SSLSocketFactoryHolder {
        public static final SSLSocketFactory INSTANCE = NaiveSSLHelper.access$200();
    }

    private static class NaiveTrustManager
    implements X509TrustManager {
        private NaiveTrustManager() {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}

