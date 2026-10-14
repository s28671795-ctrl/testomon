/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.exception.GenericCertificateException;
import nl.altindag.ssl.exception.GenericIOException;
import nl.altindag.ssl.util.CertificateUtils;
import nl.altindag.ssl.util.SSLSessionUtils;
import nl.altindag.ssl.util.TrustManagerUtils;
import nl.altindag.ssl.util.internal.CollectorsUtils;

class CertificateExtractorUtils {
    private static final Pattern CA_ISSUERS_AUTHORITY_INFO_ACCESS = Pattern.compile("(?s)^AuthorityInfoAccess\\h+\\[\\R\\s*\\[\\R.*?accessMethod:\\h+caIssuers\\R\\h*accessLocation: URIName:\\h+(https?://\\S+)", 8);
    private static CertificateExtractorUtils instance;
    private final SSLFactory sslFactoryForCertificateCapturing;
    private final SSLFactory unsafeSslFactory;
    private final SSLSocketFactory unsafeSslSocketFactory;
    private final SSLSocketFactory certificateCapturingSslSocketFactory;
    private final List<X509Certificate> certificatesCollector = new CopyOnWriteArrayList<X509Certificate>();
    private Proxy proxy;

    private CertificateExtractorUtils() {
        X509ExtendedTrustManager certificateCapturingTrustManager = TrustManagerUtils.createCertificateCapturingTrustManager(this.certificatesCollector);
        this.sslFactoryForCertificateCapturing = SSLFactory.builder().withTrustMaterial(certificateCapturingTrustManager).build();
        this.unsafeSslFactory = SSLFactory.builder().withUnsafeTrustMaterial().build();
        this.certificateCapturingSslSocketFactory = this.sslFactoryForCertificateCapturing.getSslSocketFactory();
        this.unsafeSslSocketFactory = this.unsafeSslFactory.getSslSocketFactory();
    }

    protected CertificateExtractorUtils(Proxy proxy) {
        this();
        this.proxy = proxy;
    }

    protected CertificateExtractorUtils(Proxy proxy, PasswordAuthentication passwordAuthentication) {
        this(proxy);
        FelixAuthenticator authenticator = new FelixAuthenticator(passwordAuthentication);
        Authenticator.setDefault(authenticator);
    }

    static CertificateExtractorUtils getInstance() {
        if (instance == null) {
            instance = new CertificateExtractorUtils();
        } else {
            CertificateExtractorUtils.instance.certificatesCollector.clear();
            SSLSessionUtils.invalidateCaches(CertificateExtractorUtils.instance.sslFactoryForCertificateCapturing);
        }
        return instance;
    }

    List<X509Certificate> getCertificateFromExternalSource(String url2) {
        try {
            URL parsedUrl = new URL(url2);
            if ("https".equalsIgnoreCase(parsedUrl.getProtocol())) {
                HttpsURLConnection connection = (HttpsURLConnection)this.createConnection(parsedUrl);
                connection.setSSLSocketFactory(this.certificateCapturingSslSocketFactory);
                connection.connect();
                connection.disconnect();
                List<X509Certificate> rootCa = this.getRootCaFromChainIfPossible(this.certificatesCollector);
                List<X509Certificate> list = Stream.of(this.certificatesCollector, rootCa).flatMap(Collection::stream).collect(CollectorsUtils.toUnmodifiableList());
                return list;
            }
            List<X509Certificate> list = Collections.emptyList();
            return list;
        }
        catch (IOException e) {
            throw new GenericIOException(String.format("Failed getting certificate from: [%s]", url2), e);
        }
        finally {
            SSLSessionUtils.invalidateCaches(this.sslFactoryForCertificateCapturing);
        }
    }

    private URLConnection createConnection(URL url2) throws IOException {
        return this.proxy != null ? url2.openConnection(this.proxy) : url2.openConnection();
    }

    List<X509Certificate> getRootCaFromChainIfPossible(List<X509Certificate> certificates) {
        String subject;
        X509Certificate certificate;
        String issuer;
        boolean isSelfSignedCertificate;
        if (!certificates.isEmpty() && !(isSelfSignedCertificate = (issuer = (certificate = certificates.get(certificates.size() - 1)).getIssuerX500Principal().getName()).equals(subject = certificate.getSubjectX500Principal().getName()))) {
            return this.getRootCaIfPossible(certificate);
        }
        return Collections.emptyList();
    }

    List<X509Certificate> getRootCaIfPossible(X509Certificate x509Certificate) {
        List<X509Certificate> rootCaFromAuthorityInfoAccessExtension = this.getRootCaFromAuthorityInfoAccessExtensionIfPresent(x509Certificate);
        if (!rootCaFromAuthorityInfoAccessExtension.isEmpty()) {
            return rootCaFromAuthorityInfoAccessExtension;
        }
        List<X509Certificate> rootCaFromJdkTrustedCertificates = this.getRootCaFromJdkTrustedCertificates(x509Certificate);
        if (!rootCaFromJdkTrustedCertificates.isEmpty()) {
            return rootCaFromJdkTrustedCertificates;
        }
        return Collections.emptyList();
    }

    List<X509Certificate> getRootCaFromAuthorityInfoAccessExtensionIfPresent(X509Certificate certificate) {
        String certificateContent = certificate.toString();
        Matcher caIssuersMatcher = CA_ISSUERS_AUTHORITY_INFO_ACCESS.matcher(certificateContent);
        if (caIssuersMatcher.find()) {
            String issuerLocation = caIssuersMatcher.group(1);
            return this.getCertificatesFromRemoteFile(URI.create(issuerLocation), certificate);
        }
        return Collections.emptyList();
    }

    List<X509Certificate> getCertificatesFromRemoteFile(URI uri, X509Certificate intermediateCertificate) {
        try {
            URL url2 = uri.toURL();
            URLConnection connection = this.createConnection(url2);
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection)connection).setSSLSocketFactory(this.unsafeSslSocketFactory);
            }
            InputStream inputStream2 = connection.getInputStream();
            List<X509Certificate> certificates = CertificateUtils.parseDerCertificate(inputStream2).stream().filter(X509Certificate.class::isInstance).map(X509Certificate.class::cast).filter(issuer -> this.isIssuerOfIntermediateCertificate(intermediateCertificate, (X509Certificate)issuer)).collect(CollectorsUtils.toUnmodifiableList());
            inputStream2.close();
            List<X509Certificate> list = certificates;
            return list;
        }
        catch (IOException e) {
            throw new GenericCertificateException(e);
        }
        finally {
            SSLSessionUtils.invalidateCaches(this.unsafeSslFactory);
        }
    }

    List<X509Certificate> getRootCaFromJdkTrustedCertificates(X509Certificate intermediateCertificate) {
        List<X509Certificate> jdkTrustedCertificates = CertificateUtils.getJdkTrustedCertificates();
        return jdkTrustedCertificates.stream().filter(issuer -> this.isIssuerOfIntermediateCertificate(intermediateCertificate, (X509Certificate)issuer)).collect(CollectorsUtils.toUnmodifiableList());
    }

    boolean isIssuerOfIntermediateCertificate(X509Certificate intermediateCertificate, X509Certificate issuer) {
        try {
            intermediateCertificate.verify(issuer.getPublicKey());
            return true;
        }
        catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException | CertificateException e) {
            return false;
        }
    }

    private static class FelixAuthenticator
    extends Authenticator {
        private final PasswordAuthentication passwordAuthentication;

        private FelixAuthenticator(PasswordAuthentication passwordAuthentication) {
            this.passwordAuthentication = passwordAuthentication;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return this.passwordAuthentication;
        }
    }
}

