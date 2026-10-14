/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.hostnameverifier;

import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import nl.altindag.ssl.hostnameverifier.HostnameCommon;
import nl.altindag.ssl.hostnameverifier.Hostnames;
import nl.altindag.ssl.util.internal.StringUtils;

public final class FenixHostnameVerifier
implements HostnameVerifier {
    private static final HostnameVerifier INSTANCE = new FenixHostnameVerifier();
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;

    private FenixHostnameVerifier() {
    }

    public static HostnameVerifier getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean verify(String host2, SSLSession sslSession) {
        if (!this.isAscii(host2)) {
            return false;
        }
        Optional<X509Certificate> peerCertificate = this.getPeerCertificate(sslSession);
        return peerCertificate.isPresent() && this.verify(host2, peerCertificate.get());
    }

    private boolean isAscii(String value) {
        return StandardCharsets.US_ASCII.newEncoder().canEncode(value);
    }

    private Optional<X509Certificate> getPeerCertificate(SSLSession sslSession) {
        try {
            return Arrays.stream(sslSession.getPeerCertificates()).filter(Objects::nonNull).filter(X509Certificate.class::isInstance).map(X509Certificate.class::cast).findFirst();
        }
        catch (SSLPeerUnverifiedException e) {
            return Optional.empty();
        }
    }

    private boolean verify(String host2, X509Certificate certificate) {
        if (HostnameCommon.canParseAsIpAddress(host2)) {
            return this.verifyIpAddress(host2, certificate);
        }
        return this.verifyHostname(host2, certificate);
    }

    private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
        String canonicalIpAddress = Hostnames.toCanonicalHost(ipAddress);
        if (canonicalIpAddress == null) {
            return false;
        }
        List<String> subjectAltNames = this.getSubjectAltNames(certificate, 7);
        return subjectAltNames.stream().anyMatch(subjectAltName -> canonicalIpAddress.equals(Hostnames.toCanonicalHost(subjectAltName)));
    }

    private List<String> getSubjectAltNames(X509Certificate certificate, int type) {
        try {
            Collection subjectAlternativeNames = Optional.ofNullable(certificate.getSubjectAlternativeNames()).orElseGet(Collections::emptyList);
            return subjectAlternativeNames.stream().filter(Objects::nonNull).filter(subjectAlternativeName -> subjectAlternativeName.size() == 2).filter(subjectAlternativeName -> subjectAlternativeName.get(0) instanceof Integer && (Integer)subjectAlternativeName.get(0) == type).map(subjectAlternativeName -> subjectAlternativeName.get(1)).filter(String.class::isInstance).map(String.class::cast).collect(Collectors.toList());
        }
        catch (CertificateParsingException exception) {
            return Collections.emptyList();
        }
    }

    private boolean verifyHostname(String hostname, X509Certificate certificate) {
        List<String> subjectAltNames = this.getSubjectAltNames(certificate, 2);
        return subjectAltNames.stream().anyMatch(subjectAltName -> this.verifyHostname(hostname, (String)subjectAltName));
    }

    private boolean verifyHostname(String hostname, String domainNamePattern) {
        if (this.isHostnameInValid(hostname) || this.isHostnameInValid(domainNamePattern)) {
            return false;
        }
        String resultingHostname = this.toAbsolute(hostname);
        String resultingDomainNamePattern = this.toAbsolute(domainNamePattern);
        resultingHostname = this.asciiToLowercase(resultingHostname);
        if (!(resultingDomainNamePattern = this.asciiToLowercase(resultingDomainNamePattern)).contains("*")) {
            return resultingHostname.equals(resultingDomainNamePattern);
        }
        return this.verifyWildcardPattern(resultingHostname, resultingDomainNamePattern);
    }

    private boolean isHostnameInValid(String hostname) {
        return StringUtils.isBlank(hostname) || hostname.startsWith(".") || hostname.endsWith("..");
    }

    String toAbsolute(String hostname) {
        String absoluteHostname = hostname;
        if (!absoluteHostname.endsWith(".")) {
            absoluteHostname = absoluteHostname + ".";
        }
        return absoluteHostname;
    }

    private String asciiToLowercase(String value) {
        return this.isAscii(value) ? value.toLowerCase(Locale.US) : value;
    }

    private boolean verifyWildcardPattern(String hostname, String domainNamePattern) {
        if (!domainNamePattern.startsWith("*.") || domainNamePattern.indexOf("*", 1) != -1) {
            return false;
        }
        if (hostname.length() < domainNamePattern.length()) {
            return false;
        }
        if ("*.".equals(domainNamePattern)) {
            return false;
        }
        String suffix = domainNamePattern.substring(1);
        if (!hostname.endsWith(suffix)) {
            return false;
        }
        int suffixStartIndexInHostname = hostname.length() - domainNamePattern.length();
        return suffixStartIndexInHostname <= 0 || hostname.lastIndexOf(".", suffixStartIndexInHostname - 1) == -1;
    }
}

