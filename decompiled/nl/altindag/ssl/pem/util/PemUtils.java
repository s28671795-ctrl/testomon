/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import nl.altindag.ssl.exception.GenericIOException;
import nl.altindag.ssl.pem.decryptor.PemDecryptor;
import nl.altindag.ssl.pem.decryptor.Pkcs8Decryptor;
import nl.altindag.ssl.pem.exception.CertificateParseException;
import nl.altindag.ssl.pem.exception.PemParseException;
import nl.altindag.ssl.pem.exception.PrivateKeyParseException;
import nl.altindag.ssl.pem.util.PemFormatter;
import nl.altindag.ssl.pem.util.PemType;
import nl.altindag.ssl.util.KeyManagerUtils;
import nl.altindag.ssl.util.TrustManagerUtils;
import nl.altindag.ssl.util.internal.CollectorsUtils;
import nl.altindag.ssl.util.internal.IOUtils;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.X509TrustedCertificateBlock;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;

public final class PemUtils {
    private static final String EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE = "Failed to load the certificate from the provided InputStream because it is null";
    private static final UnaryOperator<String> CERTIFICATE_NOT_FOUND_EXCEPTION_MESSAGE = certificatePath -> String.format("Failed to load the certificate from the classpath for the given path: [%s]", certificatePath);
    private static final char[] NO_PASSWORD = null;
    private static final PemUtils INSTANCE = new PemUtils(new BouncyCastleProvider(), new JcaPEMKeyConverter(), new JcaX509CertificateConverter());
    private final JcaPEMKeyConverter keyConverter;
    private final JcaX509CertificateConverter certificateConverter;

    PemUtils(BouncyCastleProvider bouncyCastleProvider, JcaPEMKeyConverter keyConverter, JcaX509CertificateConverter certificateConverter) {
        Security.addProvider(bouncyCastleProvider);
        this.keyConverter = keyConverter;
        this.certificateConverter = certificateConverter;
    }

    public static X509ExtendedTrustManager loadTrustMaterial(String ... certificatePaths) {
        return TrustManagerUtils.createTrustManager(PemUtils.loadCertificate(certificatePaths));
    }

    public static X509ExtendedTrustManager loadTrustMaterial(Path ... certificatePaths) {
        return TrustManagerUtils.createTrustManager(PemUtils.loadCertificate(certificatePaths));
    }

    public static X509ExtendedTrustManager loadTrustMaterial(InputStream ... certificateStreams) {
        return TrustManagerUtils.createTrustManager(PemUtils.loadCertificate(certificateStreams));
    }

    public static List<X509Certificate> loadCertificate(String ... certificatePaths) {
        return PemUtils.loadCertificate(certificatePaths, (T certificatePath) -> ValidationUtils.requireNotNull(IOUtils.getResourceAsStream(certificatePath), (String)CERTIFICATE_NOT_FOUND_EXCEPTION_MESSAGE.apply((String)certificatePath)));
    }

    public static List<X509Certificate> loadCertificate(Path ... certificatePaths) {
        return PemUtils.loadCertificate(certificatePaths, IOUtils::getFileAsStream);
    }

    public static List<X509Certificate> loadCertificate(InputStream ... certificateStreams) {
        return PemUtils.loadCertificate(certificateStreams, (T certificateStream) -> ValidationUtils.requireNotNull(certificateStream, EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE));
    }

    private static <T> List<X509Certificate> loadCertificate(T[] resources, Function<T, InputStream> resourceMapper) {
        return Arrays.stream(resources).map(resourceMapper).map(IOUtils::getContent).map(PemUtils::parseCertificate).flatMap(Collection::stream).collect(CollectorsUtils.toUnmodifiableList());
    }

    public static List<X509Certificate> parseCertificate(String certContent) {
        List certificates = PemUtils.parsePemContent(certContent, PemType.CERTIFICATE::equals).stream().map(PemUtils::extractCertificate).filter(Optional::isPresent).map(Optional::get).collect(CollectorsUtils.toUnmodifiableList());
        return ValidationUtils.requireNotEmpty(certificates, () -> new CertificateParseException("Received an unsupported certificate type"));
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static List<Object> parsePemContent(String pemContent, Predicate<PemType> predicate) {
        String formattedPemContent = PemFormatter.reformatIfNeeded(pemContent);
        try (StringReader stringReader = new StringReader(formattedPemContent);){
            ArrayList<Object> arrayList;
            try (PEMParser pemParser = new PEMParser(stringReader);){
                ArrayList<Object> objects = new ArrayList<Object>();
                Object object = pemParser.readObject();
                while (object != null) {
                    PemType pemType = PemType.from(object);
                    if (predicate.test(pemType)) {
                        objects.add(object);
                    }
                    object = pemParser.readObject();
                }
                arrayList = objects;
            }
            return arrayList;
        }
        catch (IOException e) {
            throw new PemParseException(e);
        }
    }

    static Optional<X509Certificate> extractCertificate(Object object) {
        try {
            X509Certificate certificate = null;
            if (object instanceof X509CertificateHolder) {
                certificate = PemUtils.getInstance().getCertificateConverter().getCertificate((X509CertificateHolder)object);
            } else if (object instanceof X509TrustedCertificateBlock) {
                X509CertificateHolder certificateHolder = ((X509TrustedCertificateBlock)object).getCertificateHolder();
                certificate = PemUtils.getInstance().getCertificateConverter().getCertificate(certificateHolder);
            }
            return Optional.ofNullable(certificate);
        }
        catch (CertificateException e) {
            throw new CertificateParseException(e);
        }
    }

    public static X509ExtendedTrustManager parseTrustMaterial(String ... certificateContents) {
        return Arrays.stream(certificateContents).map(PemUtils::parseCertificate).flatMap(Collection::stream).collect(CollectorsUtils.toListAndThen(TrustManagerUtils::createTrustManager));
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(String certificateChainPath, String privateKeyPath) {
        return PemUtils.loadIdentityMaterial(certificateChainPath, privateKeyPath, NO_PASSWORD);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(String certificateChainPath, String privateKeyPath, char[] keyPassword) {
        return PemUtils.loadIdentityMaterial(certificateChainPath, privateKeyPath, keyPassword, certificatePath -> ValidationUtils.requireNotNull(IOUtils.getResourceAsStream(certificatePath), (String)CERTIFICATE_NOT_FOUND_EXCEPTION_MESSAGE.apply((String)certificatePath)));
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(InputStream certificateChainStream, InputStream privateKeyStream) {
        return PemUtils.loadIdentityMaterial(certificateChainStream, privateKeyStream, NO_PASSWORD);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(InputStream certificateChainStream, InputStream privateKeyStream, char[] keyPassword) {
        return PemUtils.loadIdentityMaterial(certificateChainStream, privateKeyStream, keyPassword, inputStream2 -> ValidationUtils.requireNotNull(inputStream2, EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE));
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(Path certificateChainPath, Path privateKeyPath) {
        return PemUtils.loadIdentityMaterial(certificateChainPath, privateKeyPath, NO_PASSWORD);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(Path certificateChainPath, Path privateKeyPath, char[] keyPassword) {
        return PemUtils.loadIdentityMaterial(certificateChainPath, privateKeyPath, keyPassword, IOUtils::getFileAsStream);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(String identityPath) {
        return PemUtils.loadIdentityMaterial(identityPath, NO_PASSWORD);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(String identityPath, char[] keyPassword) {
        return PemUtils.loadIdentityMaterial(identityPath, keyPassword, certificatePath -> ValidationUtils.requireNotNull(IOUtils.getResourceAsStream(certificatePath), (String)CERTIFICATE_NOT_FOUND_EXCEPTION_MESSAGE.apply((String)certificatePath)));
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(Path identityPath) {
        return PemUtils.loadIdentityMaterial(identityPath, NO_PASSWORD);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(Path identityPath, char[] keyPassword) {
        return PemUtils.loadIdentityMaterial(identityPath, keyPassword, IOUtils::getFileAsStream);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(InputStream identityStream) {
        return PemUtils.loadIdentityMaterial(identityStream, NO_PASSWORD);
    }

    public static X509ExtendedKeyManager loadIdentityMaterial(InputStream identityStream, char[] keyPassword) {
        return PemUtils.loadIdentityMaterial(identityStream, keyPassword, inputStream2 -> ValidationUtils.requireNotNull(inputStream2, EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE));
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static <T> X509ExtendedKeyManager loadIdentityMaterial(T certificateChain, T privateKey, char[] keyPassword, Function<T, InputStream> resourceMapper) {
        try (InputStream certificateChainStream = resourceMapper.apply(certificateChain);){
            X509ExtendedKeyManager x509ExtendedKeyManager;
            block14: {
                InputStream privateKeyStream = resourceMapper.apply(privateKey);
                try {
                    String certificateChainContent = IOUtils.getContent(certificateChainStream);
                    String privateKeyContent = IOUtils.getContent(privateKeyStream);
                    x509ExtendedKeyManager = PemUtils.parseIdentityMaterial(certificateChainContent, privateKeyContent, keyPassword);
                    if (privateKeyStream == null) break block14;
                }
                catch (Throwable throwable) {
                    if (privateKeyStream != null) {
                        try {
                            privateKeyStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                privateKeyStream.close();
            }
            return x509ExtendedKeyManager;
        }
        catch (IOException exception) {
            throw new GenericIOException(exception);
        }
    }

    private static <T> X509ExtendedKeyManager loadIdentityMaterial(T identity, char[] keyPassword, Function<T, InputStream> resourceMapper) {
        X509ExtendedKeyManager x509ExtendedKeyManager;
        block8: {
            InputStream identityStream = resourceMapper.apply(identity);
            try {
                String identityContent = IOUtils.getContent(identityStream);
                x509ExtendedKeyManager = PemUtils.parseIdentityMaterial(identityContent, identityContent, keyPassword);
                if (identityStream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (identityStream != null) {
                        try {
                            identityStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException exception) {
                    throw new GenericIOException(exception);
                }
            }
            identityStream.close();
        }
        return x509ExtendedKeyManager;
    }

    public static X509ExtendedKeyManager parseIdentityMaterial(String identityContent) {
        return PemUtils.parseIdentityMaterial(identityContent, identityContent, null);
    }

    public static X509ExtendedKeyManager parseIdentityMaterial(String identityContent, char[] keyPassword) {
        return PemUtils.parseIdentityMaterial(identityContent, identityContent, keyPassword);
    }

    public static X509ExtendedKeyManager parseIdentityMaterial(String certificateChainContent, String privateKeyContent, char[] keyPassword) {
        PrivateKey privateKey = PemUtils.parsePrivateKey(privateKeyContent, keyPassword);
        Certificate[] certificateChain = PemUtils.parseCertificate(certificateChainContent).toArray(new Certificate[0]);
        return KeyManagerUtils.createKeyManager(privateKey, certificateChain);
    }

    public static PrivateKey loadPrivateKey(String identityPath) {
        return PemUtils.loadPrivateKey(identityPath, NO_PASSWORD);
    }

    public static PrivateKey loadPrivateKey(String identityPath, char[] keyPassword) {
        return PemUtils.loadPrivateKey(identityPath, keyPassword, certificatePath -> ValidationUtils.requireNotNull(IOUtils.getResourceAsStream(certificatePath), (String)CERTIFICATE_NOT_FOUND_EXCEPTION_MESSAGE.apply((String)certificatePath)));
    }

    public static PrivateKey loadPrivateKey(Path identityPath) {
        return PemUtils.loadPrivateKey(identityPath, NO_PASSWORD);
    }

    public static PrivateKey loadPrivateKey(Path identityPath, char[] keyPassword) {
        return PemUtils.loadPrivateKey(identityPath, keyPassword, IOUtils::getFileAsStream);
    }

    public static PrivateKey loadPrivateKey(InputStream identityStream) {
        return PemUtils.loadPrivateKey(identityStream, NO_PASSWORD);
    }

    public static PrivateKey loadPrivateKey(InputStream identityStream, char[] keyPassword) {
        return PemUtils.loadPrivateKey(identityStream, keyPassword, inputStream2 -> ValidationUtils.requireNotNull(inputStream2, EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE));
    }

    private static <T> PrivateKey loadPrivateKey(T privateKey, char[] keyPassword, Function<T, InputStream> resourceMapper) {
        PrivateKey privateKey2;
        block8: {
            InputStream privateKeyStream = resourceMapper.apply(privateKey);
            try {
                String privateKeyContent = IOUtils.getContent(privateKeyStream);
                privateKey2 = PemUtils.parsePrivateKey(privateKeyContent, keyPassword);
                if (privateKeyStream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (privateKeyStream != null) {
                        try {
                            privateKeyStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException exception) {
                    throw new GenericIOException(exception);
                }
            }
            privateKeyStream.close();
        }
        return privateKey2;
    }

    public static PrivateKey parsePrivateKey(String identityContent) {
        return PemUtils.parsePrivateKey(identityContent, NO_PASSWORD);
    }

    public static PrivateKey parsePrivateKey(String identityContent, char[] keyPassword) {
        return PemUtils.parsePemContent(identityContent, PemType.KEY::equals).stream().map(object -> PemUtils.extractPrivateKeyInfo(object, keyPassword)).filter(Optional::isPresent).map(Optional::get).findFirst().map(PemUtils::extractPrivateKey).orElseThrow(() -> new PrivateKeyParseException("Received an unsupported private key type"));
    }

    static Optional<PrivateKeyInfo> extractPrivateKeyInfo(Object object, char[] keyPassword) {
        try {
            PrivateKeyInfo privateKeyInfo = null;
            if (object instanceof PrivateKeyInfo) {
                privateKeyInfo = (PrivateKeyInfo)object;
            } else if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
                privateKeyInfo = Pkcs8Decryptor.getInstance().andThen(((PKCS8EncryptedPrivateKeyInfo)object)::decryptPrivateKeyInfo).apply(keyPassword);
            } else if (object instanceof PEMKeyPair) {
                privateKeyInfo = ((PEMKeyPair)object).getPrivateKeyInfo();
            } else if (object instanceof PEMEncryptedKeyPair) {
                privateKeyInfo = PemDecryptor.getInstance().andThen(((PEMEncryptedKeyPair)object)::decryptKeyPair).andThen(PEMKeyPair::getPrivateKeyInfo).apply(keyPassword);
            }
            return Optional.ofNullable(privateKeyInfo);
        }
        catch (IOException | OperatorCreationException | PKCSException e) {
            throw new PrivateKeyParseException(e);
        }
    }

    private static PrivateKey extractPrivateKey(PrivateKeyInfo privateKeyInfo) {
        try {
            return PemUtils.getInstance().getKeyConverter().getPrivateKey(privateKeyInfo);
        }
        catch (PEMException exception) {
            throw new PrivateKeyParseException(exception);
        }
    }

    static PemUtils getInstance() {
        return INSTANCE;
    }

    private JcaPEMKeyConverter getKeyConverter() {
        return this.keyConverter;
    }

    private JcaX509CertificateConverter getCertificateConverter() {
        return this.certificateConverter;
    }
}

