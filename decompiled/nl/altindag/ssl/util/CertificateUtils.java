/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import nl.altindag.ssl.exception.GenericCertificateException;
import nl.altindag.ssl.exception.GenericIOException;
import nl.altindag.ssl.util.CertificateExtractorUtils;
import nl.altindag.ssl.util.TrustManagerUtils;
import nl.altindag.ssl.util.internal.CollectorsUtils;
import nl.altindag.ssl.util.internal.IOUtils;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CertificateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateUtils.class);
    private static final String CERTIFICATE_TYPE = "X.509";
    private static final String P7B_HEADER = "-----BEGIN PKCS7-----";
    private static final String P7B_FOOTER = "-----END PKCS7-----";
    private static final String PEM_HEADER = "-----BEGIN CERTIFICATE-----";
    private static final String PEM_FOOTER = "-----END CERTIFICATE-----";
    private static final Pattern PEM_PATTERN = Pattern.compile("-----BEGIN CERTIFICATE-----(.*?)-----END CERTIFICATE-----", 32);
    private static final Pattern P7B_PATTERN = Pattern.compile("-----BEGIN PKCS7-----(.*?)-----END PKCS7-----", 32);
    private static final String EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE = "Failed to load the certificate from the provided InputStream because it is null";
    private static final UnaryOperator<String> CERTIFICATE_NOT_FOUND_EXCEPTION_MESSAGE = certificatePath -> String.format("Failed to load the certificate from the classpath for the given path: [%s]", certificatePath);
    private static final String MAX_64_CHARACTER_LINE_SPLITTER = "(?<=\\G.{64})";
    private static final String EMPTY = "";

    private CertificateUtils() {
    }

    public static <T extends Certificate> String generateAlias(T certificate) {
        if (certificate instanceof X509Certificate) {
            return ((X509Certificate)certificate).getSubjectX500Principal().getName("CANONICAL").replace(" ", "-").replace(",", "_").replace("'", EMPTY).replaceAll("[.*\\\\]+", EMPTY);
        }
        return UUID.randomUUID().toString().toLowerCase(Locale.US);
    }

    public static <T extends Certificate> Map<String, T> generateAliases(List<T> certificates) {
        HashMap<String, Certificate> aliasToCertificate = new HashMap<String, Certificate>();
        for (Certificate certificate : certificates) {
            String alias = CertificateUtils.generateUniqueAlias(certificate, aliasToCertificate::containsKey);
            aliasToCertificate.put(alias, certificate);
        }
        return Collections.unmodifiableMap(aliasToCertificate);
    }

    public static <T extends Certificate> String generateUniqueAlias(T certificate, Predicate<String> aliasPredicate) {
        String initialAlias;
        String alias = initialAlias = CertificateUtils.generateAlias(certificate);
        int counter = 0;
        while (aliasPredicate.test(alias)) {
            alias = String.format("%s-%d", initialAlias, counter);
            ++counter;
        }
        return alias;
    }

    public static <T extends Certificate> void write(Path destination, T certificate) {
        try {
            byte[] encodedCertificate = certificate.getEncoded();
            IOUtils.write(destination, encodedCertificate);
        }
        catch (CertificateEncodingException e) {
            throw new GenericCertificateException(e);
        }
    }

    public static List<Certificate> loadCertificate(String ... certificatePaths) {
        return CertificateUtils.loadCertificate((T certificatePath) -> ValidationUtils.requireNotNull(CertificateUtils.class.getClassLoader().getResourceAsStream((String)certificatePath), (String)CERTIFICATE_NOT_FOUND_EXCEPTION_MESSAGE.apply((String)certificatePath)), certificatePaths);
    }

    public static List<Certificate> loadCertificate(Path ... certificatePaths) {
        return CertificateUtils.loadCertificate((T certificatePath) -> {
            try {
                return Files.newInputStream(certificatePath, StandardOpenOption.READ);
            }
            catch (IOException exception) {
                throw new GenericIOException(exception);
            }
        }, certificatePaths);
    }

    public static List<Certificate> loadCertificate(InputStream ... certificateStreams) {
        return CertificateUtils.loadCertificate((T certificateStream) -> ValidationUtils.requireNotNull(certificateStream, EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE), certificateStreams);
    }

    private static <T> List<Certificate> loadCertificate(Function<T, InputStream> resourceMapper, T[] resources) {
        ArrayList<Certificate> certificates = new ArrayList<Certificate>();
        for (T resource : resources) {
            try (InputStream certificateStream = resourceMapper.apply(resource);){
                certificates.addAll(CertificateUtils.parseCertificate(certificateStream));
            }
            catch (Exception e) {
                throw new GenericIOException(e);
            }
        }
        return Collections.unmodifiableList(certificates);
    }

    private static List<Certificate> parseCertificate(InputStream certificateStream) {
        byte[] certificateData = IOUtils.copyToByteArray(certificateStream);
        String certificateContent = new String(certificateData, StandardCharsets.UTF_8);
        List<Certificate> certificates = CertificateUtils.isPemFormatted(certificateContent) ? CertificateUtils.parsePemCertificate(certificateContent) : (CertificateUtils.isP7bFormatted(certificateContent) ? CertificateUtils.parseP7bCertificate(certificateContent) : CertificateUtils.parseDerCertificate(new ByteArrayInputStream(certificateData)));
        return certificates;
    }

    private static boolean isPemFormatted(String certificateContent) {
        return PEM_PATTERN.matcher(certificateContent).find();
    }

    private static boolean isP7bFormatted(String certificateContent) {
        return P7B_PATTERN.matcher(certificateContent).find();
    }

    public static List<Certificate> parsePemCertificate(String certificateContent) {
        Matcher pemMatcher = PEM_PATTERN.matcher(certificateContent);
        return CertificateUtils.parseCertificate(pemMatcher);
    }

    public static List<Certificate> parseP7bCertificate(String certificateContent) {
        Matcher p7bMatcher = P7B_PATTERN.matcher(certificateContent);
        return CertificateUtils.parseCertificate(p7bMatcher);
    }

    private static List<Certificate> parseCertificate(Matcher certificateMatcher) {
        ArrayList<Certificate> certificates = new ArrayList<Certificate>();
        while (certificateMatcher.find()) {
            String certificate = certificateMatcher.group(1);
            String sanitizedCertificate = certificate.replaceAll("[\\n|\\r]+", EMPTY).trim();
            byte[] decodedCertificate = Base64.getDecoder().decode(sanitizedCertificate);
            ByteArrayInputStream certificateAsInputStream = new ByteArrayInputStream(decodedCertificate);
            List<Certificate> parsedCertificates = CertificateUtils.parseDerCertificate(certificateAsInputStream);
            certificates.addAll(parsedCertificates);
            IOUtils.closeSilently(certificateAsInputStream);
        }
        return Collections.unmodifiableList(certificates);
    }

    public static List<Certificate> parseDerCertificate(InputStream certificateStream) {
        List<Certificate> list;
        BufferedInputStream bufferedCertificateStream = new BufferedInputStream(certificateStream);
        try {
            list = CertificateFactory.getInstance(CERTIFICATE_TYPE).generateCertificates(bufferedCertificateStream).stream().collect(CollectorsUtils.toUnmodifiableList());
        }
        catch (Throwable throwable) {
            try {
                try {
                    bufferedCertificateStream.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException | CertificateException e) {
                LOGGER.debug("There is no valid certificate present to parse. Please make sure to supply a valid der formatted certificate", e);
                return Collections.emptyList();
            }
        }
        bufferedCertificateStream.close();
        return list;
    }

    public static List<X509Certificate> getJdkTrustedCertificates() {
        return Stream.of(TrustManagerUtils.createTrustManagerWithJdkTrustedCertificates().getAcceptedIssuers()).collect(CollectorsUtils.toUnmodifiableList());
    }

    public static List<X509Certificate> getSystemTrustedCertificates() {
        return TrustManagerUtils.createTrustManagerWithSystemTrustedCertificates().map(X509TrustManager::getAcceptedIssuers).map(Arrays::asList).map(Collections::unmodifiableList).orElseGet(Collections::emptyList);
    }

    public static List<X509Certificate> getCertificatesFromExternalSource(String url2) {
        return CertificateExtractorUtils.getInstance().getCertificateFromExternalSource(url2);
    }

    public static List<X509Certificate> getCertificatesFromExternalSource(Proxy proxy, String url2) {
        return new CertificateExtractorUtils(proxy).getCertificateFromExternalSource(url2);
    }

    public static List<X509Certificate> getCertificatesFromExternalSource(Proxy proxy, PasswordAuthentication passwordAuthentication, String url2) {
        return new CertificateExtractorUtils(proxy, passwordAuthentication).getCertificateFromExternalSource(url2);
    }

    public static List<String> getCertificatesFromExternalSourceAsPem(String url2) {
        return CertificateUtils.getCertificatesFromExternalSource(url2).stream().map(CertificateUtils::convertToPem).collect(CollectorsUtils.toUnmodifiableList());
    }

    public static List<String> getCertificatesFromExternalSourceAsPem(Proxy proxy, String url2) {
        return CertificateUtils.getCertificatesFromExternalSource(proxy, url2).stream().map(CertificateUtils::convertToPem).collect(CollectorsUtils.toUnmodifiableList());
    }

    public static List<String> getCertificatesFromExternalSourceAsPem(Proxy proxy, PasswordAuthentication passwordAuthentication, String url2) {
        return CertificateUtils.getCertificatesFromExternalSource(proxy, passwordAuthentication, url2).stream().map(CertificateUtils::convertToPem).collect(CollectorsUtils.toUnmodifiableList());
    }

    public static Map<String, List<X509Certificate>> getCertificatesFromExternalSources(String ... urls2) {
        return CertificateUtils.getCertificatesFromExternalSources(Arrays.asList(urls2));
    }

    public static Map<String, List<X509Certificate>> getCertificatesFromExternalSources(Proxy proxy, String ... urls2) {
        return CertificateUtils.getCertificatesFromExternalSources(proxy, Arrays.asList(urls2));
    }

    public static Map<String, List<X509Certificate>> getCertificatesFromExternalSources(Proxy proxy, PasswordAuthentication passwordAuthentication, String ... urls2) {
        return CertificateUtils.getCertificatesFromExternalSources(proxy, passwordAuthentication, Arrays.asList(urls2));
    }

    public static Map<String, List<X509Certificate>> getCertificatesFromExternalSources(List<String> urls2) {
        return urls2.stream().map(url2 -> new AbstractMap.SimpleEntry<String, List<X509Certificate>>((String)url2, CertificateUtils.getCertificatesFromExternalSource(url2))).collect(Collectors.collectingAndThen(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue), Collections::unmodifiableMap));
    }

    public static Map<String, List<X509Certificate>> getCertificatesFromExternalSources(Proxy proxy, List<String> urls2) {
        CertificateExtractorUtils certificateExtractorUtils = new CertificateExtractorUtils(proxy);
        return urls2.stream().map(url2 -> new AbstractMap.SimpleEntry<String, List<X509Certificate>>((String)url2, certificateExtractorUtils.getCertificateFromExternalSource((String)url2))).collect(Collectors.collectingAndThen(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue), Collections::unmodifiableMap));
    }

    public static Map<String, List<X509Certificate>> getCertificatesFromExternalSources(Proxy proxy, PasswordAuthentication passwordAuthentication, List<String> urls2) {
        CertificateExtractorUtils certificateExtractorUtils = new CertificateExtractorUtils(proxy, passwordAuthentication);
        return urls2.stream().map(url2 -> new AbstractMap.SimpleEntry<String, List<X509Certificate>>((String)url2, certificateExtractorUtils.getCertificateFromExternalSource((String)url2))).collect(Collectors.collectingAndThen(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue), Collections::unmodifiableMap));
    }

    public static Map<String, List<String>> getCertificatesFromExternalSourcesAsPem(String ... urls2) {
        return CertificateUtils.getCertificatesFromExternalSourcesAsPem(Arrays.asList(urls2));
    }

    public static Map<String, List<String>> getCertificatesFromExternalSourcesAsPem(Proxy proxy, String ... urls2) {
        return CertificateUtils.getCertificatesFromExternalSourcesAsPem(proxy, Arrays.asList(urls2));
    }

    public static Map<String, List<String>> getCertificatesFromExternalSourcesAsPem(Proxy proxy, PasswordAuthentication passwordAuthentication, String ... urls2) {
        return CertificateUtils.getCertificatesFromExternalSourcesAsPem(proxy, passwordAuthentication, Arrays.asList(urls2));
    }

    public static Map<String, List<String>> getCertificatesFromExternalSourcesAsPem(List<String> urls2) {
        Map<String, List> certificates = CertificateUtils.getCertificatesFromExternalSources(urls2).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> CertificateUtils.convertToPem((List)entry.getValue())));
        return Collections.unmodifiableMap(certificates);
    }

    public static Map<String, List<String>> getCertificatesFromExternalSourcesAsPem(Proxy proxy, List<String> urls2) {
        Map<String, List> certificates = CertificateUtils.getCertificatesFromExternalSources(proxy, urls2).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> CertificateUtils.convertToPem((List)entry.getValue())));
        return Collections.unmodifiableMap(certificates);
    }

    public static Map<String, List<String>> getCertificatesFromExternalSourcesAsPem(Proxy proxy, PasswordAuthentication passwordAuthentication, List<String> urls2) {
        Map<String, List> certificates = CertificateUtils.getCertificatesFromExternalSources(proxy, passwordAuthentication, urls2).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> CertificateUtils.convertToPem((List)entry.getValue())));
        return Collections.unmodifiableMap(certificates);
    }

    public static List<String> convertToPem(List<X509Certificate> certificates) {
        return certificates.stream().map(CertificateUtils::convertToPem).collect(CollectorsUtils.toUnmodifiableList());
    }

    public static String convertToPem(Certificate certificate) {
        try {
            byte[] encodedCertificate = certificate.getEncoded();
            byte[] base64EncodedCertificate = Base64.getEncoder().encode(encodedCertificate);
            String parsedCertificate = new String(base64EncodedCertificate);
            List certificateContainer = Stream.of(parsedCertificate.split(MAX_64_CHARACTER_LINE_SPLITTER)).collect(CollectorsUtils.toModifiableList());
            certificateContainer.add(0, PEM_HEADER);
            certificateContainer.add(PEM_FOOTER);
            if (certificate instanceof X509Certificate) {
                X509Certificate x509Certificate = (X509Certificate)certificate;
                X500Principal issuer = x509Certificate.getIssuerX500Principal();
                certificateContainer.add(0, String.format("issuer=%s", issuer.getName()));
                X500Principal subject = x509Certificate.getSubjectX500Principal();
                certificateContainer.add(0, String.format("subject=%s", subject.getName()));
            }
            return String.join((CharSequence)System.lineSeparator(), certificateContainer);
        }
        catch (CertificateEncodingException e) {
            throw new GenericCertificateException(e);
        }
    }
}

