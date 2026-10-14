/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.http;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.org.apache.commons.validator.routines.InetAddressValidator;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class HttpUtils {
    public static final String AWS_S3_PREFIX = "^(((bucket\\.|accesspoint\\.)vpce(-(?!_)[a-z_\\d]+(?<!-)(?<!_))+\\.s3\\.)|((?!s3)(?!-)(?!_)[a-z_\\d-]{1,63}(?<!-)(?<!_)\\.)s3-control(-(?!_)[a-z_\\d]+(?<!-)(?<!_))*\\.|(s3(-(?!_)[a-z_\\d]+(?<!-)(?<!_))*\\.))";
    public static final Pattern HOSTNAME_REGEX = Pattern.compile("^((?!-)(?!_)[a-z_\\d-]{1,63}(?<!-)(?<!_)\\.)*((?!_)(?!-)[a-z_\\d-]{1,63}(?<!-)(?<!_))$", 2);
    public static final Pattern AWS_ENDPOINT_REGEX = Pattern.compile(".*\\.amazonaws\\.com(|\\.cn)$", 2);
    public static final Pattern AWS_S3_ENDPOINT_REGEX = Pattern.compile("^(((bucket\\.|accesspoint\\.)vpce(-(?!_)[a-z_\\d]+(?<!-)(?<!_))+\\.s3\\.)|((?!s3)(?!-)(?!_)[a-z_\\d-]{1,63}(?<!-)(?<!_)\\.)s3-control(-(?!_)[a-z_\\d]+(?<!-)(?<!_))*\\.|(s3(-(?!_)[a-z_\\d]+(?<!-)(?<!_))*\\.))((?!s3)(?!-)(?!_)[a-z_\\d-]{1,63}(?<!-)(?<!_)\\.)*amazonaws\\.com(|\\.cn)$", 2);
    public static final Pattern AWS_ELB_ENDPOINT_REGEX = Pattern.compile("^(?!-)(?!_)[a-z_\\d-]{1,63}(?<!-)(?<!_)\\.(?!-)(?!_)[a-z_\\d-]{1,63}(?<!-)(?<!_)\\.elb\\.amazonaws\\.com$", 2);
    public static final Pattern AWS_S3_PREFIX_REGEX = Pattern.compile("^(((bucket\\.|accesspoint\\.)vpce(-(?!_)[a-z_\\d]+(?<!-)(?<!_))+\\.s3\\.)|((?!s3)(?!-)(?!_)[a-z_\\d-]{1,63}(?<!-)(?<!_)\\.)s3-control(-(?!_)[a-z_\\d]+(?<!-)(?<!_))*\\.|(s3(-(?!_)[a-z_\\d]+(?<!-)(?<!_))*\\.))", 2);
    public static final Pattern REGION_REGEX = Pattern.compile("^((?!_)(?!-)[a-z_\\d-]{1,63}(?<!-)(?<!_))$", 2);
    public static final byte[] EMPTY_BODY = new byte[0];

    public static void validateNotNull(Object arg, String argName) {
        if (arg == null) {
            throw new IllegalArgumentException(argName + " must not be null.");
        }
    }

    public static void validateNotEmptyString(String arg, String argName) {
        HttpUtils.validateNotNull(arg, argName);
        if (arg.isEmpty()) {
            throw new IllegalArgumentException(argName + " must be a non-empty string.");
        }
    }

    public static void validateNullOrNotEmptyString(String arg, String argName) {
        if (arg != null && arg.isEmpty()) {
            throw new IllegalArgumentException(argName + " must be a non-empty string.");
        }
    }

    public static void validateHostnameOrIPAddress(String endpoint) {
        if (InetAddressValidator.getInstance().isValid(endpoint)) {
            return;
        }
        if (!HOSTNAME_REGEX.matcher(endpoint).find()) {
            throw new IllegalArgumentException("invalid hostname " + endpoint);
        }
    }

    public static void validateUrl(HttpUrl url2) {
        if (!url2.encodedPath().equals("/")) {
            throw new IllegalArgumentException("no path allowed in endpoint " + url2);
        }
    }

    public static HttpUrl getBaseUrl(String endpoint) {
        HttpUtils.validateNotEmptyString(endpoint, "endpoint");
        HttpUrl url2 = HttpUrl.parse(endpoint);
        if (url2 == null) {
            HttpUtils.validateHostnameOrIPAddress(endpoint);
            url2 = new HttpUrl.Builder().scheme("https").host(endpoint).build();
        } else {
            HttpUtils.validateUrl(url2);
        }
        return url2;
    }

    public static String getHostHeader(HttpUrl url2) {
        String host2 = url2.host();
        if (InetAddressValidator.getInstance().isValidInet6Address(host2)) {
            host2 = "[" + host2 + "]";
        }
        if (url2.scheme().equals("http") && url2.port() == 80 || url2.scheme().equals("https") && url2.port() == 443) {
            return host2;
        }
        return host2 + ":" + url2.port();
    }

    private static OkHttpClient enableJKSPKCS12Certificates(OkHttpClient httpClient, String trustStorePath, String trustStorePassword, String keyStorePath, String keyStorePassword, String keyStoreType) throws GeneralSecurityException, IOException {
        if (trustStorePath == null || trustStorePath.isEmpty()) {
            throw new IllegalArgumentException("trust store path must be provided");
        }
        if (trustStorePassword == null) {
            throw new IllegalArgumentException("trust store password must be provided");
        }
        if (keyStorePath == null || keyStorePath.isEmpty()) {
            throw new IllegalArgumentException("key store path must be provided");
        }
        if (keyStorePassword == null) {
            throw new IllegalArgumentException("key store password must be provided");
        }
        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyStore trustStore = KeyStore.getInstance("JKS");
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        try (FileInputStream trustInput = new FileInputStream(trustStorePath);
             FileInputStream keyInput = new FileInputStream(keyStorePath);){
            trustStore.load(trustInput, trustStorePassword.toCharArray());
            keyStore.load(keyInput, keyStorePassword.toCharArray());
        }
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        return httpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)trustManagerFactory.getTrustManagers()[0]).build();
    }

    public static OkHttpClient enableJKSCertificates(OkHttpClient httpClient, String trustStorePath, String trustStorePassword, String keyStorePath, String keyStorePassword) throws GeneralSecurityException, IOException {
        return HttpUtils.enableJKSPKCS12Certificates(httpClient, trustStorePath, trustStorePassword, keyStorePath, keyStorePassword, "JKS");
    }

    public static OkHttpClient enablePKCS12Certificates(OkHttpClient httpClient, String trustStorePath, String trustStorePassword, String keyStorePath, String keyStorePassword) throws GeneralSecurityException, IOException {
        return HttpUtils.enableJKSPKCS12Certificates(httpClient, trustStorePath, trustStorePassword, keyStorePath, keyStorePassword, "PKCS12");
    }

    public static OkHttpClient enableExternalCertificates(OkHttpClient httpClient, String filename) throws GeneralSecurityException, IOException {
        Collection<? extends Certificate> certificates = null;
        try (FileInputStream fis = new FileInputStream(filename);){
            certificates = CertificateFactory.getInstance("X.509").generateCertificates(fis);
        }
        if (certificates == null || certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }
        char[] password = "password".toCharArray();
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return httpClient.newBuilder().sslSocketFactory(sslSocketFactory, (X509TrustManager)trustManagers[0]).build();
    }

    public static OkHttpClient newDefaultHttpClient(long connectTimeout, long writeTimeout, long readTimeout) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(connectTimeout, TimeUnit.MILLISECONDS).writeTimeout(writeTimeout, TimeUnit.MILLISECONDS).readTimeout(readTimeout, TimeUnit.MILLISECONDS).protocols(Arrays.asList(Protocol.HTTP_1_1)).build();
        String filename = System.getenv("SSL_CERT_FILE");
        if (filename != null && !filename.isEmpty()) {
            try {
                httpClient = HttpUtils.enableExternalCertificates(httpClient, filename);
            }
            catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
        return httpClient;
    }

    @SuppressFBWarnings(value={"SIC"}, justification="Should not be used in production anyways.")
    public static OkHttpClient disableCertCheck(OkHttpClient client) throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return client.newBuilder().sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]).hostnameVerifier(new HostnameVerifier(){

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).build();
    }

    public static OkHttpClient setTimeout(OkHttpClient client, long connectTimeout, long writeTimeout, long readTimeout) {
        return client.newBuilder().connectTimeout(connectTimeout, TimeUnit.MILLISECONDS).writeTimeout(writeTimeout, TimeUnit.MILLISECONDS).readTimeout(readTimeout, TimeUnit.MILLISECONDS).build();
    }
}

