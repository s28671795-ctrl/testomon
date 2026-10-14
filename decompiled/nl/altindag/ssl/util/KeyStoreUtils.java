/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.exception.GenericKeyStoreException;
import nl.altindag.ssl.util.CertificateUtils;
import nl.altindag.ssl.util.LinuxCertificateUtils;
import nl.altindag.ssl.util.MacCertificateUtils;
import nl.altindag.ssl.util.OperatingSystem;
import nl.altindag.ssl.util.internal.CollectorsUtils;
import nl.altindag.ssl.util.internal.IOUtils;
import nl.altindag.ssl.util.internal.StringUtils;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KeyStoreUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyStoreUtils.class);
    public static final String DUMMY_PASSWORD = "dummy-password";
    private static final String KEYSTORE_TYPE = "PKCS12";
    private static final String EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE = "Failed to load the keystore from the provided InputStream because it is null";
    private static final UnaryOperator<String> KEYSTORE_NOT_FOUND_EXCEPTION_MESSAGE = certificatePath -> String.format("Failed to load the keystore from the classpath for the given path: [%s]", certificatePath);
    private static final String EMPTY_TRUST_MANAGER_FOR_TRUSTSTORE_EXCEPTION = "Could not create TrustStore because the provided TrustManager does not contain any trusted certificates";
    private static final String EMPTY_CERTIFICATES_EXCEPTION = "Could not create TrustStore because certificate is absent";

    private KeyStoreUtils() {
    }

    public static KeyStore loadKeyStore(String keystorePath, char[] keystorePassword) {
        return KeyStoreUtils.loadKeyStore(keystorePath, keystorePassword, KeyStore.getDefaultType());
    }

    public static KeyStore loadKeyStore(String keystorePath, char[] keystorePassword, String keystoreType) {
        KeyStore keyStore;
        block8: {
            InputStream keystoreInputStream = KeyStoreUtils.class.getClassLoader().getResourceAsStream(keystorePath);
            try {
                keyStore = KeyStoreUtils.loadKeyStore(ValidationUtils.requireNotNull(keystoreInputStream, (String)KEYSTORE_NOT_FOUND_EXCEPTION_MESSAGE.apply(keystorePath)), keystorePassword, keystoreType);
                if (keystoreInputStream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (keystoreInputStream != null) {
                        try {
                            keystoreInputStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    throw new GenericKeyStoreException(e);
                }
            }
            keystoreInputStream.close();
        }
        return keyStore;
    }

    public static KeyStore loadKeyStore(Path keystorePath, char[] keystorePassword) {
        return KeyStoreUtils.loadKeyStore(keystorePath, keystorePassword, KeyStore.getDefaultType());
    }

    public static KeyStore loadKeyStore(Path keystorePath, char[] keystorePassword, String keystoreType) {
        KeyStore keyStore;
        block8: {
            InputStream keystoreInputStream = Files.newInputStream(keystorePath, StandardOpenOption.READ);
            try {
                keyStore = KeyStoreUtils.loadKeyStore(keystoreInputStream, keystorePassword, keystoreType);
                if (keystoreInputStream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (keystoreInputStream != null) {
                        try {
                            keystoreInputStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    throw new GenericKeyStoreException(e);
                }
            }
            keystoreInputStream.close();
        }
        return keyStore;
    }

    public static KeyStore loadKeyStore(InputStream keystoreInputStream, char[] keystorePassword) {
        return KeyStoreUtils.loadKeyStore(ValidationUtils.requireNotNull(keystoreInputStream, EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE), keystorePassword, KeyStore.getDefaultType());
    }

    public static KeyStore loadKeyStore(InputStream keystoreInputStream, char[] keystorePassword, String keystoreType) {
        try {
            KeyStore keystore = KeyStore.getInstance(keystoreType);
            keystore.load(ValidationUtils.requireNotNull(keystoreInputStream, EMPTY_INPUT_STREAM_EXCEPTION_MESSAGE), keystorePassword);
            return keystore;
        }
        catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    public static KeyStore createIdentityStore(Key privateKey, char[] privateKeyPassword, String alias, List<? extends Certificate> certificateChain) {
        return KeyStoreUtils.createIdentityStore((Key)privateKey, (char[])privateKeyPassword, (String)alias, (Certificate[])certificateChain.toArray(new Certificate[0]));
    }

    public static KeyStore createIdentityStore(Key privateKey, char[] privateKeyPassword, List<? extends Certificate> certificateChain) {
        return KeyStoreUtils.createIdentityStore((Key)privateKey, (char[])privateKeyPassword, null, (Certificate[])certificateChain.toArray(new Certificate[0]));
    }

    @SafeVarargs
    public static <T extends Certificate> KeyStore createIdentityStore(Key privateKey, char[] privateKeyPassword, T ... certificateChain) {
        return KeyStoreUtils.createIdentityStore((Key)privateKey, (char[])privateKeyPassword, null, certificateChain);
    }

    @SafeVarargs
    public static <T extends Certificate> KeyStore createIdentityStore(Key privateKey, char[] privateKeyPassword, String alias, T ... certificateChain) {
        try {
            KeyStore keyStore = KeyStoreUtils.createKeyStore();
            String privateKeyAlias = StringUtils.isBlank(alias) ? CertificateUtils.generateAlias(certificateChain[0]) : alias;
            keyStore.setKeyEntry(privateKeyAlias, privateKey, privateKeyPassword, (Certificate[])certificateChain);
            return keyStore;
        }
        catch (KeyStoreException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    public static KeyStore createKeyStore() {
        return KeyStoreUtils.createKeyStore(DUMMY_PASSWORD.toCharArray());
    }

    public static KeyStore createKeyStore(char[] keyStorePassword) {
        return KeyStoreUtils.createKeyStore(KEYSTORE_TYPE, keyStorePassword);
    }

    public static KeyStore createKeyStore(String keyStoreType, char[] keyStorePassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, keyStorePassword);
            return keyStore;
        }
        catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    @SafeVarargs
    public static <T extends X509TrustManager> KeyStore createTrustStore(T ... trustManagers) {
        ArrayList<X509Certificate> certificates = new ArrayList<X509Certificate>();
        for (T trustManager : trustManagers) {
            certificates.addAll(Arrays.asList(trustManager.getAcceptedIssuers()));
        }
        return KeyStoreUtils.createTrustStore(ValidationUtils.requireNotEmpty(certificates, EMPTY_TRUST_MANAGER_FOR_TRUSTSTORE_EXCEPTION));
    }

    @SafeVarargs
    public static <T extends Certificate> KeyStore createTrustStore(T ... certificates) {
        return KeyStoreUtils.createTrustStore(Arrays.asList(certificates));
    }

    public static <T extends Certificate> KeyStore createTrustStore(List<T> certificates) {
        try {
            KeyStore trustStore = KeyStoreUtils.createKeyStore();
            for (Certificate certificate : ValidationUtils.requireNotEmpty(certificates, EMPTY_CERTIFICATES_EXCEPTION)) {
                String alias = CertificateUtils.generateAlias(certificate);
                boolean shouldAddCertificate = true;
                if (trustStore.containsAlias(alias)) {
                    for (int number = 0; number <= 1000; ++number) {
                        String mayBeUniqueAlias = alias + "-" + number;
                        if (!trustStore.containsAlias(mayBeUniqueAlias)) {
                            alias = mayBeUniqueAlias;
                            shouldAddCertificate = true;
                            break;
                        }
                        shouldAddCertificate = false;
                    }
                }
                if (!shouldAddCertificate) continue;
                trustStore.setCertificateEntry(alias, certificate);
            }
            return trustStore;
        }
        catch (KeyStoreException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    public static List<KeyStore> loadSystemKeyStores() {
        ArrayList<KeyStore> keyStores = new ArrayList<KeyStore>();
        OperatingSystem operatingSystem = OperatingSystem.get();
        switch (operatingSystem) {
            case MAC: {
                KeyStoreUtils.createKeyStoreIfAvailable("KeychainStore", null).ifPresent(keyStores::add);
                List<Certificate> systemTrustedCertificates = MacCertificateUtils.getCertificates();
                if (systemTrustedCertificates.isEmpty()) break;
                KeyStore systemTrustStore = KeyStoreUtils.createTrustStore(systemTrustedCertificates);
                keyStores.add(systemTrustStore);
                break;
            }
            case LINUX: {
                List<Certificate> certificates = LinuxCertificateUtils.getCertificates();
                if (certificates.isEmpty()) break;
                KeyStore linuxTrustStore = KeyStoreUtils.createTrustStore(certificates);
                keyStores.add(linuxTrustStore);
                break;
            }
            case ANDROID: {
                KeyStoreUtils.createKeyStoreIfAvailable("AndroidCAStore", null).ifPresent(keyStores::add);
                break;
            }
            case WINDOWS: {
                Stream.of("Windows-ROOT", "Windows-ROOT-LOCALMACHINE", "Windows-ROOT-CURRENTUSER", "Windows-MY", "Windows-MY-CURRENTUSER", "Windows-MY-LOCALMACHINE").map(keystoreType -> KeyStoreUtils.createKeyStoreIfAvailable(keystoreType, null)).filter(Optional::isPresent).map(Optional::get).forEach(keyStores::add);
                break;
            }
            default: {
                String resolvedOsName = OperatingSystem.UNKNOWN.getResolvedOsName();
                LOGGER.warn("No system KeyStores available for [{}]", (Object)resolvedOsName);
                return Collections.emptyList();
            }
        }
        if (LOGGER.isDebugEnabled()) {
            int totalTrustedCertificates = keyStores.stream().mapToInt(KeyStoreUtils::countAmountOfTrustMaterial).sum();
            LOGGER.debug("Loaded [{}] system trusted certificates", (Object)totalTrustedCertificates);
        }
        return Collections.unmodifiableList(keyStores);
    }

    static Optional<KeyStore> createKeyStoreIfAvailable(String keyStoreType, char[] keyStorePassword) {
        try {
            KeyStore keyStore = KeyStoreUtils.createKeyStore(keyStoreType, keyStorePassword);
            if (LOGGER.isDebugEnabled()) {
                int totalTrustedCertificates = KeyStoreUtils.countAmountOfTrustMaterial(keyStore);
                LOGGER.debug("Successfully loaded KeyStore of the type [{}] having [{}] entries", (Object)keyStoreType, (Object)totalTrustedCertificates);
            }
            return Optional.of(keyStore);
        }
        catch (Exception ignored) {
            LOGGER.debug("Failed to load KeyStore of the type [{}]", (Object)keyStoreType);
            return Optional.empty();
        }
    }

    public static List<Certificate> getCertificates(KeyStore keyStore) {
        return KeyStoreUtils.getAliasToCertificate(keyStore).values().stream().collect(CollectorsUtils.toUnmodifiableList());
    }

    public static Map<String, Certificate> getAliasToCertificate(KeyStore keyStore) {
        try {
            HashMap<String, Certificate> aliasToCertificate = new HashMap<String, Certificate>();
            List<String> aliases = KeyStoreUtils.getAliases(keyStore);
            for (String alias : aliases) {
                if (!keyStore.isCertificateEntry(alias)) continue;
                Certificate certificate = keyStore.getCertificate(alias);
                aliasToCertificate.put(alias, certificate);
            }
            return Collections.unmodifiableMap(aliasToCertificate);
        }
        catch (KeyStoreException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    public static List<String> getAliases(KeyStore keyStore) {
        try {
            ArrayList<String> destinationAliases = new ArrayList<String>();
            Enumeration<String> sourceAliases = keyStore.aliases();
            while (sourceAliases.hasMoreElements()) {
                String alias = sourceAliases.nextElement();
                destinationAliases.add(alias);
            }
            return Collections.unmodifiableList(destinationAliases);
        }
        catch (KeyStoreException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    public static <T extends Certificate> boolean containsCertificate(KeyStore keyStore, T certificate) {
        try {
            return keyStore.getCertificateAlias(certificate) != null;
        }
        catch (KeyStoreException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    public static void write(Path destination, KeyStore keyStore, char[] password) {
        IOUtils.write(destination, outputStream2 -> keyStore.store((OutputStream)outputStream2, password));
    }

    public static int countAmountOfTrustMaterial(KeyStore keyStore) {
        return KeyStoreUtils.amountOfSpecifiedMaterial(keyStore, KeyStore::isCertificateEntry, Integer.MAX_VALUE);
    }

    public static int countAmountOfIdentityMaterial(KeyStore keyStore) {
        return KeyStoreUtils.amountOfSpecifiedMaterial(keyStore, KeyStore::isKeyEntry, Integer.MAX_VALUE);
    }

    public static boolean containsTrustMaterial(KeyStore keyStore) {
        return KeyStoreUtils.amountOfSpecifiedMaterial(keyStore, KeyStore::isCertificateEntry, 1) > 0;
    }

    public static boolean containsIdentityMaterial(KeyStore keyStore) {
        return KeyStoreUtils.amountOfSpecifiedMaterial(keyStore, KeyStore::isKeyEntry, 1) > 0;
    }

    private static int amountOfSpecifiedMaterial(KeyStore keyStore, KeyStoreBiPredicate<KeyStore, String> predicate, int upperBoundaryForMaterialCounter) {
        try {
            int materialCounter = 0;
            List<String> aliases = KeyStoreUtils.getAliases(keyStore);
            for (String alias : aliases) {
                if (materialCounter >= upperBoundaryForMaterialCounter || !predicate.test(keyStore, alias)) continue;
                ++materialCounter;
            }
            return materialCounter;
        }
        catch (KeyStoreException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    private static interface KeyStoreBiPredicate<T extends KeyStore, U> {
        public boolean test(T var1, U var2) throws KeyStoreException;
    }
}

