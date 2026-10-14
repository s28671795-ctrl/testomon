/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.exception.GenericKeyStoreException;
import nl.altindag.ssl.exception.GenericSecurityException;
import nl.altindag.ssl.model.HostnameVerifierParameters;
import nl.altindag.ssl.model.KeyStoreHolder;
import nl.altindag.ssl.model.TrustManagerParameters;
import nl.altindag.ssl.model.internal.SSLMaterial;
import nl.altindag.ssl.sslcontext.FenixSSLContext;
import nl.altindag.ssl.trustmanager.trustoptions.TrustAnchorTrustOptions;
import nl.altindag.ssl.trustmanager.trustoptions.TrustStoreTrustOptions;
import nl.altindag.ssl.trustmanager.validator.ChainAndAuthTypeValidator;
import nl.altindag.ssl.trustmanager.validator.ChainAndAuthTypeWithSSLEngineValidator;
import nl.altindag.ssl.trustmanager.validator.ChainAndAuthTypeWithSocketValidator;
import nl.altindag.ssl.util.HostnameVerifierUtils;
import nl.altindag.ssl.util.KeyManagerUtils;
import nl.altindag.ssl.util.KeyStoreUtils;
import nl.altindag.ssl.util.SSLContextUtils;
import nl.altindag.ssl.util.SSLParametersUtils;
import nl.altindag.ssl.util.SSLSessionUtils;
import nl.altindag.ssl.util.TrustManagerUtils;
import nl.altindag.ssl.util.internal.CollectorsUtils;
import nl.altindag.ssl.util.internal.StringUtils;
import nl.altindag.ssl.util.internal.UriUtils;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SSLFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSLFactory.class);
    private final SSLMaterial sslMaterial;

    private SSLFactory(SSLMaterial sslMaterial) {
        this.sslMaterial = sslMaterial;
    }

    public SSLContext getSslContext() {
        return this.sslMaterial.getSslContext();
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslMaterial.getSslContext().getSocketFactory();
    }

    public SSLServerSocketFactory getSslServerSocketFactory() {
        return this.sslMaterial.getSslContext().getServerSocketFactory();
    }

    public Optional<X509ExtendedKeyManager> getKeyManager() {
        return Optional.ofNullable(this.sslMaterial.getKeyManager());
    }

    public Optional<KeyManagerFactory> getKeyManagerFactory() {
        return this.getKeyManager().map(KeyManagerUtils::createKeyManagerFactory);
    }

    public Optional<X509ExtendedTrustManager> getTrustManager() {
        return Optional.ofNullable(this.sslMaterial.getTrustManager());
    }

    public Optional<TrustManagerFactory> getTrustManagerFactory() {
        return this.getTrustManager().map(TrustManagerUtils::createTrustManagerFactory);
    }

    public List<X509Certificate> getTrustedCertificates() {
        return this.getTrustManager().map(X509TrustManager::getAcceptedIssuers).map(Arrays::asList).map(Collections::unmodifiableList).orElseGet(Collections::emptyList);
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.sslMaterial.getHostnameVerifier();
    }

    public List<String> getCiphers() {
        return this.sslMaterial.getCiphers();
    }

    public List<String> getProtocols() {
        return this.sslMaterial.getProtocols();
    }

    public SSLParameters getSslParameters() {
        return SSLParametersUtils.copy(this.sslMaterial.getSslParameters());
    }

    public SSLEngine getSSLEngine() {
        return this.getSSLEngine(null, null);
    }

    public SSLEngine getSSLEngine(String peerHost, Integer peerPort) {
        if (Objects.nonNull(peerHost) && Objects.nonNull(peerPort)) {
            return this.sslMaterial.getSslContext().createSSLEngine(peerHost, peerPort);
        }
        return this.sslMaterial.getSslContext().createSSLEngine();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final String TRUST_STORE_VALIDATION_EXCEPTION_MESSAGE = "TrustStore details are empty, which are required to be present when SSL/TLS is enabled";
        private static final String IDENTITY_VALIDATION_EXCEPTION_MESSAGE = "Identity details are empty, which are required to be present when SSL/TLS is enabled";
        private static final String IDENTITY_AND_TRUST_MATERIAL_VALIDATION_EXCEPTION_MESSAGE = "Could not create instance of SSLFactory because Identity and Trust material are not present. Please provide at least a Trust material.";
        private static final String CERTIFICATE_VALIDATION_EXCEPTION_MESSAGE = "Failed to load the certificate(s). No certificate has been provided.";
        private static final String SYSTEM_PROPERTY_VALIDATION_EXCEPTION_MESSAGE = "Failed to load the System property for [%s] because it does not contain any value";
        private String sslContextAlgorithm = "TLS";
        private Provider securityProvider = null;
        private String securityProviderName = null;
        private SecureRandom secureRandom = null;
        private HostnameVerifier hostnameVerifier = HostnameVerifierUtils.createDefault();
        private Predicate<HostnameVerifierParameters> hostnameVerifierEnhancer = null;
        private final List<KeyStoreHolder> identities = new ArrayList<KeyStoreHolder>();
        private final List<KeyStore> trustStores = new ArrayList<KeyStore>();
        private final List<X509ExtendedKeyManager> identityManagers = new ArrayList<X509ExtendedKeyManager>();
        private final List<X509ExtendedTrustManager> trustManagers = new ArrayList<X509ExtendedTrustManager>();
        private final SSLParameters sslParameters = new SSLParameters();
        private final Map<String, List<URI>> preferredAliasToHost = new HashMap<String, List<URI>>();
        private final List<String> protocols = new ArrayList<String>();
        private final List<String> ciphers = new ArrayList<String>();
        private boolean swappableKeyManagerEnabled = false;
        private boolean swappableTrustManagerEnabled = false;
        private boolean loggingKeyManagerEnabled = false;
        private boolean loggingTrustManagerEnabled = false;
        private int sessionTimeoutInSeconds = -1;
        private int sessionCacheSizeInBytes = -1;
        private ChainAndAuthTypeValidator chainAndAuthTypeValidator = null;
        private ChainAndAuthTypeWithSocketValidator chainAndAuthTypeWithSocketValidator = null;
        private ChainAndAuthTypeWithSSLEngineValidator chainAndAuthTypeWithSSLEngineValidator = null;
        private Predicate<TrustManagerParameters> trustManagerParametersValidator = null;

        private Builder() {
        }

        public Builder withSystemTrustMaterial() {
            TrustManagerUtils.createTrustManagerWithSystemTrustedCertificates().ifPresent(this.trustManagers::add);
            return this;
        }

        public Builder withDefaultTrustMaterial() {
            this.trustManagers.add(TrustManagerUtils.createTrustManagerWithJdkTrustedCertificates());
            return this;
        }

        public Builder withSystemPropertyDerivedTrustMaterial() {
            return this.withSystemPropertyDerivedMaterial("javax.net.ssl.trustStore", "javax.net.ssl.trustStorePassword", "javax.net.ssl.trustStoreType", "javax.net.ssl.trustStoreProvider", this::withTrustMaterial);
        }

        public Builder withUnsafeTrustMaterial() {
            return this.withTrustingAllCertificatesWithoutValidation();
        }

        public Builder withDummyTrustMaterial() {
            this.trustManagers.add(TrustManagerUtils.createDummyTrustManager());
            return this;
        }

        public Builder withSwappableTrustMaterial() {
            this.swappableTrustManagerEnabled = true;
            return this;
        }

        public Builder withLoggingTrustMaterial() {
            this.loggingTrustManagerEnabled = true;
            return this;
        }

        public <T extends X509TrustManager> Builder withTrustMaterial(T trustManager) {
            this.trustManagers.add(TrustManagerUtils.wrapIfNeeded(trustManager));
            return this;
        }

        public <T extends ManagerFactoryParameters> Builder withTrustMaterial(T managerFactoryParameters) {
            this.trustManagers.add(TrustManagerUtils.createTrustManager(managerFactoryParameters));
            return this;
        }

        public <T extends X509TrustManager> Builder withTrustMaterial(T trustManager, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            KeyStore trustStore = KeyStoreUtils.createTrustStore((Certificate[])trustManager.getAcceptedIssuers());
            return this.withTrustMaterial(trustStore, trustOptions);
        }

        public <T extends TrustManagerFactory> Builder withTrustMaterial(T trustManagerFactory) {
            X509ExtendedTrustManager trustManager = TrustManagerUtils.getTrustManager(trustManagerFactory);
            this.trustManagers.add(trustManager);
            return this;
        }

        public Builder withTrustMaterial(String trustStorePath, char[] trustStorePassword) {
            return this.withTrustMaterial(trustStorePath, trustStorePassword, KeyStore.getDefaultType());
        }

        public Builder withTrustMaterial(String trustStorePath, char[] trustStorePassword, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            return this.withTrustMaterial(trustStorePath, trustStorePassword, KeyStore.getDefaultType(), trustOptions);
        }

        public Builder withTrustMaterial(String trustStorePath, char[] trustStorePassword, String trustStoreType) {
            if (StringUtils.isBlank(trustStorePath) || StringUtils.isBlank(trustStoreType)) {
                throw new GenericKeyStoreException(TRUST_STORE_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore trustStore = KeyStoreUtils.loadKeyStore(trustStorePath, trustStorePassword, trustStoreType);
            this.trustStores.add(trustStore);
            return this;
        }

        public Builder withTrustMaterial(String trustStorePath, char[] trustStorePassword, String trustStoreType, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            if (StringUtils.isBlank(trustStorePath) || StringUtils.isBlank(trustStoreType)) {
                throw new GenericKeyStoreException(TRUST_STORE_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore trustStore = KeyStoreUtils.loadKeyStore(trustStorePath, trustStorePassword, trustStoreType);
            return this.withTrustMaterial(trustStore, trustOptions);
        }

        public Builder withTrustMaterial(Path trustStorePath, char[] trustStorePassword) {
            return this.withTrustMaterial(trustStorePath, trustStorePassword, KeyStore.getDefaultType());
        }

        public Builder withTrustMaterial(Path trustStorePath, char[] trustStorePassword, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            return this.withTrustMaterial(trustStorePath, trustStorePassword, KeyStore.getDefaultType(), trustOptions);
        }

        public Builder withTrustMaterial(Path trustStorePath, char[] trustStorePassword, String trustStoreType) {
            if (Objects.isNull(trustStorePath) || StringUtils.isBlank(trustStoreType)) {
                throw new GenericKeyStoreException(TRUST_STORE_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore trustStore = KeyStoreUtils.loadKeyStore(trustStorePath, trustStorePassword, trustStoreType);
            this.trustStores.add(trustStore);
            return this;
        }

        private Builder withTrustMaterial(Path trustStorePath, char[] trustStorePassword, String trustStoreType, String securityProviderName) {
            if (Objects.isNull(trustStorePath)) {
                throw new GenericKeyStoreException(TRUST_STORE_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore trustStore = KeyStoreUtils.loadKeyStore(trustStorePath, trustStorePassword, trustStoreType);
            X509ExtendedTrustManager trustManager = securityProviderName == null ? TrustManagerUtils.createTrustManager(trustStore, TrustManagerFactory.getDefaultAlgorithm()) : TrustManagerUtils.createTrustManager(trustStore, TrustManagerFactory.getDefaultAlgorithm(), securityProviderName);
            this.trustManagers.add(trustManager);
            return this;
        }

        public Builder withTrustMaterial(Path trustStorePath, char[] trustStorePassword, String trustStoreType, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            if (Objects.isNull(trustStorePath) || StringUtils.isBlank(trustStoreType)) {
                throw new GenericKeyStoreException(TRUST_STORE_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore trustStore = KeyStoreUtils.loadKeyStore(trustStorePath, trustStorePassword, trustStoreType);
            return this.withTrustMaterial(trustStore, trustOptions);
        }

        public Builder withTrustMaterial(InputStream trustStoreStream, char[] trustStorePassword) {
            return this.withTrustMaterial(trustStoreStream, trustStorePassword, KeyStore.getDefaultType());
        }

        public Builder withTrustMaterial(InputStream trustStoreStream, char[] trustStorePassword, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            return this.withTrustMaterial(trustStoreStream, trustStorePassword, KeyStore.getDefaultType(), trustOptions);
        }

        public Builder withTrustMaterial(InputStream trustStoreStream, char[] trustStorePassword, String trustStoreType) {
            KeyStore trustStore = KeyStoreUtils.loadKeyStore(trustStoreStream, trustStorePassword, trustStoreType);
            this.trustStores.add(trustStore);
            return this;
        }

        public Builder withTrustMaterial(InputStream trustStoreStream, char[] trustStorePassword, String trustStoreType, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            KeyStore trustStore = KeyStoreUtils.loadKeyStore(trustStoreStream, trustStorePassword, trustStoreType);
            return this.withTrustMaterial(trustStore, trustOptions);
        }

        public Builder withTrustMaterial(KeyStore trustStore) {
            this.validateKeyStore(trustStore, TRUST_STORE_VALIDATION_EXCEPTION_MESSAGE);
            this.trustStores.add(trustStore);
            return this;
        }

        public Builder withTrustMaterial(KeyStore trustStore, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            try {
                CertPathTrustManagerParameters certPathTrustManagerParameters = trustOptions.apply(trustStore);
                return this.withTrustMaterial((TrustManagerFactory)((Object)certPathTrustManagerParameters));
            }
            catch (Exception e) {
                throw new GenericSecurityException(e);
            }
        }

        public Builder withTrustMaterial(Set<X509Certificate> certificates, TrustAnchorTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            try {
                Set<TrustAnchor> trustAnchors = certificates.stream().map(certificate -> new TrustAnchor((X509Certificate)certificate, null)).collect(Collectors.toSet());
                CertPathTrustManagerParameters certPathTrustManagerParameters = trustOptions.apply(trustAnchors);
                return this.withTrustMaterial((TrustManagerFactory)((Object)certPathTrustManagerParameters));
            }
            catch (Exception e) {
                throw new GenericSecurityException(e);
            }
        }

        @SafeVarargs
        public final <T extends Certificate> Builder withTrustMaterial(T ... certificates) {
            return this.withTrustMaterial(Arrays.asList(certificates));
        }

        public final <T extends Certificate> Builder withTrustMaterial(T[] certificates, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            return this.withTrustMaterial(Arrays.asList(certificates), trustOptions);
        }

        public <T extends Certificate> Builder withTrustMaterial(List<T> certificates) {
            KeyStore trustStore = KeyStoreUtils.createTrustStore(ValidationUtils.requireNotEmpty(certificates, CERTIFICATE_VALIDATION_EXCEPTION_MESSAGE));
            this.trustStores.add(trustStore);
            return this;
        }

        public <T extends Certificate> Builder withTrustMaterial(List<T> certificates, TrustStoreTrustOptions<? extends CertPathTrustManagerParameters> trustOptions) {
            KeyStore trustStore = KeyStoreUtils.createTrustStore(ValidationUtils.requireNotEmpty(certificates, CERTIFICATE_VALIDATION_EXCEPTION_MESSAGE));
            return this.withTrustMaterial(trustStore, trustOptions);
        }

        public Builder withSystemPropertyDerivedIdentityMaterial() {
            return this.withSystemPropertyDerivedMaterial("javax.net.ssl.keyStore", "javax.net.ssl.keyStorePassword", "javax.net.ssl.keyStoreType", "javax.net.ssl.keyStoreProvider", this::withIdentityMaterial);
        }

        public Builder withIdentityMaterial(String identityStorePath, char[] identityStorePassword) {
            return this.withIdentityMaterial(identityStorePath, identityStorePassword, identityStorePassword, KeyStore.getDefaultType());
        }

        public Builder withIdentityMaterial(String identityStorePath, char[] identityStorePassword, char[] identityPassword) {
            return this.withIdentityMaterial(identityStorePath, identityStorePassword, identityPassword, KeyStore.getDefaultType());
        }

        public Builder withIdentityMaterial(String identityStorePath, char[] identityStorePassword, String identityStoreType) {
            return this.withIdentityMaterial(identityStorePath, identityStorePassword, identityStorePassword, identityStoreType);
        }

        public Builder withIdentityMaterial(String identityStorePath, char[] identityStorePassword, char[] identityPassword, String identityStoreType) {
            if (StringUtils.isBlank(identityStorePath) || StringUtils.isBlank(identityStoreType)) {
                throw new GenericKeyStoreException(IDENTITY_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore identity = KeyStoreUtils.loadKeyStore(identityStorePath, identityStorePassword, identityStoreType);
            KeyStoreHolder identityHolder = new KeyStoreHolder(identity, identityPassword);
            this.identities.add(identityHolder);
            return this;
        }

        public Builder withIdentityMaterial(Path identityStorePath, char[] identityStorePassword) {
            return this.withIdentityMaterial(identityStorePath, identityStorePassword, identityStorePassword, KeyStore.getDefaultType());
        }

        public Builder withIdentityMaterial(Path identityStorePath, char[] identityStorePassword, char[] identityPassword) {
            return this.withIdentityMaterial(identityStorePath, identityStorePassword, identityPassword, KeyStore.getDefaultType());
        }

        public Builder withIdentityMaterial(Path identityStorePath, char[] identityStorePassword, String identityStoreType) {
            return this.withIdentityMaterial(identityStorePath, identityStorePassword, identityStorePassword, identityStoreType);
        }

        public Builder withIdentityMaterial(Path identityStorePath, char[] identityStorePassword, char[] identityPassword, String identityStoreType) {
            if (Objects.isNull(identityStorePath) || StringUtils.isBlank(identityStoreType)) {
                throw new GenericKeyStoreException(IDENTITY_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore identity = KeyStoreUtils.loadKeyStore(identityStorePath, identityStorePassword, identityStoreType);
            KeyStoreHolder identityHolder = new KeyStoreHolder(identity, identityPassword);
            this.identities.add(identityHolder);
            return this;
        }

        private Builder withIdentityMaterial(Path identityStorePath, char[] identityStorePassword, String identityStoreType, String securityProviderName) {
            if (Objects.isNull(identityStorePath)) {
                throw new GenericKeyStoreException(IDENTITY_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore identity = KeyStoreUtils.loadKeyStore(identityStorePath, identityStorePassword, identityStoreType);
            X509ExtendedKeyManager keyManager = securityProviderName == null ? KeyManagerUtils.createKeyManager(identity, identityStorePassword, KeyManagerFactory.getDefaultAlgorithm()) : KeyManagerUtils.createKeyManager(identity, identityStorePassword, KeyManagerFactory.getDefaultAlgorithm(), securityProviderName);
            this.identityManagers.add(keyManager);
            return this;
        }

        public Builder withIdentityMaterial(InputStream identityStream, char[] identityStorePassword) {
            return this.withIdentityMaterial(identityStream, identityStorePassword, identityStorePassword);
        }

        public Builder withIdentityMaterial(InputStream identityStream, char[] identityStorePassword, char[] identityPassword) {
            return this.withIdentityMaterial(identityStream, identityStorePassword, identityPassword, KeyStore.getDefaultType());
        }

        public Builder withIdentityMaterial(InputStream identityStream, char[] identityStorePassword, String identityStoreType) {
            return this.withIdentityMaterial(identityStream, identityStorePassword, identityStorePassword, identityStoreType);
        }

        public Builder withIdentityMaterial(InputStream identityStream, char[] identityStorePassword, char[] identityPassword, String identityStoreType) {
            if (Objects.isNull(identityStream) || StringUtils.isBlank(identityStoreType)) {
                throw new GenericKeyStoreException(IDENTITY_VALIDATION_EXCEPTION_MESSAGE);
            }
            KeyStore identity = KeyStoreUtils.loadKeyStore(identityStream, identityStorePassword, identityStoreType);
            KeyStoreHolder identityHolder = new KeyStoreHolder(identity, identityPassword);
            this.identities.add(identityHolder);
            return this;
        }

        public Builder withIdentityMaterial(KeyStore identityStore, char[] identityPassword) {
            this.validateKeyStore(identityStore, IDENTITY_VALIDATION_EXCEPTION_MESSAGE);
            KeyStoreHolder identityHolder = new KeyStoreHolder(identityStore, identityPassword);
            this.identities.add(identityHolder);
            return this;
        }

        @SafeVarargs
        public final <T extends Certificate> Builder withIdentityMaterial(Key privateKey, char[] privateKeyPassword, T ... certificateChain) {
            return this.withIdentityMaterial(privateKey, privateKeyPassword, null, (Certificate[])certificateChain);
        }

        @SafeVarargs
        public final <T extends Certificate> Builder withIdentityMaterial(Key privateKey, char[] privateKeyPassword, String alias, T ... certificateChain) {
            return this.withIdentityMaterial(privateKey, privateKeyPassword, alias, Arrays.asList(certificateChain));
        }

        public final <T extends Certificate> Builder withIdentityMaterial(Key privateKey, char[] privateKeyPassword, List<T> certificateChain) {
            return this.withIdentityMaterial(privateKey, privateKeyPassword, (String)null, certificateChain);
        }

        public final <T extends Certificate> Builder withIdentityMaterial(Key privateKey, char[] privateKeyPassword, String alias, List<T> certificateChain) {
            KeyStore identityStore = KeyStoreUtils.createIdentityStore(privateKey, privateKeyPassword, alias, certificateChain);
            this.identities.add(new KeyStoreHolder(identityStore, privateKeyPassword));
            return this;
        }

        public <T extends X509KeyManager> Builder withIdentityMaterial(T keyManager) {
            this.identityManagers.add(KeyManagerUtils.wrapIfNeeded(keyManager));
            return this;
        }

        public <T extends KeyManagerFactory> Builder withIdentityMaterial(T keyManagerFactory) {
            X509ExtendedKeyManager keyManager = KeyManagerUtils.getKeyManager(keyManagerFactory);
            this.identityManagers.add(keyManager);
            return this;
        }

        public Builder withDummyIdentityMaterial() {
            this.identityManagers.add(KeyManagerUtils.createDummyKeyManager());
            return this;
        }

        public Builder withSwappableIdentityMaterial() {
            this.swappableKeyManagerEnabled = true;
            return this;
        }

        public Builder withLoggingIdentityMaterial() {
            this.loggingKeyManagerEnabled = true;
            return this;
        }

        public Builder withInflatableTrustMaterial() {
            this.trustManagers.add(TrustManagerUtils.createInflatableTrustManager());
            return this;
        }

        @Deprecated
        public Builder withInflatableTrustMaterial(Path trustStorePath, char[] trustStorePassword, String trustStoreType, BiPredicate<X509Certificate[], String> certificateAndAuthTypeTrustPredicate) {
            this.trustManagers.add(TrustManagerUtils.createInflatableTrustManager(trustStorePath, trustStorePassword, trustStoreType, certificateAndAuthTypeTrustPredicate));
            return this;
        }

        public Builder withInflatableTrustMaterial(Path trustStorePath, char[] trustStorePassword, String trustStoreType, Predicate<TrustManagerParameters> trustManagerParametersPredicate) {
            this.trustManagers.add(TrustManagerUtils.createInflatableTrustManager(trustStorePath, trustStorePassword, trustStoreType, trustManagerParametersPredicate));
            return this;
        }

        private void validateKeyStore(KeyStore keyStore, String exceptionMessage) {
            if (Objects.isNull(keyStore)) {
                throw new GenericKeyStoreException(exceptionMessage);
            }
        }

        public Builder withIdentityRoute(String alias, String ... hosts) {
            return this.withIdentityRoute(alias, Arrays.stream(hosts).map(URI::create).collect(Collectors.toList()));
        }

        public Builder withIdentityRoute(Map<String, List<String>> aliasesToHosts) {
            aliasesToHosts.entrySet().stream().map(aliasToHosts -> new AbstractMap.SimpleEntry((String)aliasToHosts.getKey(), ((List)aliasToHosts.getValue()).stream().map(URI::create).collect(Collectors.toList()))).forEach(aliasToHosts -> this.withIdentityRoute((String)aliasToHosts.getKey(), (List)aliasToHosts.getValue()));
            return this;
        }

        private Builder withIdentityRoute(String alias, List<URI> hosts) {
            if (StringUtils.isBlank(alias)) {
                throw new IllegalArgumentException("alias should be present");
            }
            ValidationUtils.requireNotEmpty(hosts, String.format("At least one host should be present. No host(s) found for the given alias: [%s]", alias));
            for (URI host2 : hosts) {
                UriUtils.validate(host2);
                if (this.preferredAliasToHost.containsKey(alias)) {
                    this.preferredAliasToHost.get(alias).add(host2);
                    continue;
                }
                this.preferredAliasToHost.put(alias, new ArrayList<URI>(Collections.singletonList(host2)));
            }
            return this;
        }

        public <T extends HostnameVerifier> Builder withHostnameVerifier(T hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder withUnsafeHostnameVerifier() {
            this.hostnameVerifier = HostnameVerifierUtils.createUnsafe();
            return this;
        }

        public Builder withHostnameVerifierEnhancer(Predicate<HostnameVerifierParameters> hostnameVerifierParametersValidator) {
            this.hostnameVerifierEnhancer = hostnameVerifierParametersValidator;
            return this;
        }

        public Builder withCiphers(String ... ciphers) {
            this.ciphers.addAll(Arrays.asList(ciphers));
            return this;
        }

        public Builder withSystemPropertyDerivedCiphers() {
            this.ciphers.addAll(this.extractPropertyValues("https.cipherSuites"));
            return this;
        }

        public Builder withProtocols(String ... protocols) {
            this.protocols.addAll(Arrays.asList(protocols));
            return this;
        }

        public Builder withSystemPropertyDerivedProtocols() {
            this.protocols.addAll(this.extractPropertyValues("https.protocols"));
            return this;
        }

        private List<String> extractPropertyValues(String systemProperty) {
            String propertyValue = ValidationUtils.requireNotBlank(System.getProperty(systemProperty), String.format(SYSTEM_PROPERTY_VALIDATION_EXCEPTION_MESSAGE, systemProperty));
            List propertyValues = Arrays.stream(propertyValue.split(",")).map(String::trim).filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
            return ValidationUtils.requireNotEmpty(propertyValues, String.format(SYSTEM_PROPERTY_VALIDATION_EXCEPTION_MESSAGE, systemProperty));
        }

        public Builder withNeedClientAuthentication() {
            return this.withNeedClientAuthentication(true);
        }

        public Builder withNeedClientAuthentication(boolean needClientAuthentication) {
            this.sslParameters.setNeedClientAuth(needClientAuthentication);
            return this;
        }

        public Builder withWantClientAuthentication() {
            return this.withWantClientAuthentication(true);
        }

        public Builder withWantClientAuthentication(boolean wantClientAuthentication) {
            this.sslParameters.setWantClientAuth(wantClientAuthentication);
            return this;
        }

        public Builder withSessionTimeout(int timeoutInSeconds) {
            this.sessionTimeoutInSeconds = timeoutInSeconds;
            return this;
        }

        public Builder withSessionCacheSize(int cacheSizeInBytes) {
            this.sessionCacheSizeInBytes = cacheSizeInBytes;
            return this;
        }

        public Builder withSslContextAlgorithm(String sslContextAlgorithm) {
            this.sslContextAlgorithm = sslContextAlgorithm;
            return this;
        }

        public <T extends Provider> Builder withSecurityProvider(T securityProvider) {
            this.securityProvider = securityProvider;
            return this;
        }

        public Builder withSecurityProvider(String securityProviderName) {
            this.securityProviderName = securityProviderName;
            return this;
        }

        public <T extends SecureRandom> Builder withSecureRandom(T secureRandom) {
            this.secureRandom = secureRandom;
            return this;
        }

        public Builder withTrustingAllCertificatesWithoutValidation() {
            this.trustManagers.add(TrustManagerUtils.createUnsafeTrustManager());
            LOGGER.debug("UnsafeTrustManager is being used. Client/Server certificates will be accepted without validation.");
            return this;
        }

        @Deprecated
        public Builder withTrustEnhancer(ChainAndAuthTypeValidator validator) {
            this.chainAndAuthTypeValidator = validator;
            return this;
        }

        @Deprecated
        public Builder withTrustEnhancer(ChainAndAuthTypeWithSocketValidator validator) {
            this.chainAndAuthTypeWithSocketValidator = validator;
            return this;
        }

        @Deprecated
        public Builder withTrustEnhancer(ChainAndAuthTypeWithSSLEngineValidator validator) {
            this.chainAndAuthTypeWithSSLEngineValidator = validator;
            return this;
        }

        public Builder withTrustEnhancer(Predicate<TrustManagerParameters> validator) {
            this.trustManagerParametersValidator = validator;
            return this;
        }

        private Builder withSystemPropertyDerivedMaterial(String keyStorePathProperty, String keyStorePasswordProperty, String keyStoreTypeProperty, String securityProviderNameProperty, QuadConsumer<Path, char[], String, String> keyStorePropertyConsumer) {
            Path keystore = Optional.ofNullable(System.getProperty(keyStorePathProperty)).map(String::trim).filter(StringUtils::isNotBlank).map(x$0 -> Paths.get(x$0, new String[0])).orElse(null);
            char[] keystorePassword = Optional.ofNullable(System.getProperty(keyStorePasswordProperty)).map(String::trim).filter(StringUtils::isNotBlank).map(String::toCharArray).orElse(null);
            String keystoreType = Optional.ofNullable(System.getProperty(keyStoreTypeProperty)).map(String::trim).filter(StringUtils::isNotBlank).orElseGet(KeyStore::getDefaultType);
            String securityProvideName = Optional.ofNullable(System.getProperty(securityProviderNameProperty)).map(String::trim).filter(StringUtils::isNotBlank).orElse(null);
            keyStorePropertyConsumer.accept(keystore, keystorePassword, keystoreType, securityProvideName);
            return this;
        }

        public SSLFactory build() {
            if (!this.isIdentityMaterialPresent() && !this.isTrustMaterialPresent()) {
                throw new GenericSecurityException(IDENTITY_AND_TRUST_MATERIAL_VALIDATION_EXCEPTION_MESSAGE);
            }
            X509ExtendedKeyManager keyManager = this.isIdentityMaterialPresent() ? this.createKeyManager() : null;
            X509ExtendedTrustManager trustManager = this.isTrustMaterialPresent() ? this.createTrustManager() : null;
            SSLContext baseSslContext = SSLContextUtils.createSslContext(keyManager, trustManager, this.secureRandom, this.sslContextAlgorithm, this.securityProviderName, this.securityProvider);
            if (this.sessionTimeoutInSeconds >= 0) {
                SSLSessionUtils.updateSessionTimeout(baseSslContext, this.sessionTimeoutInSeconds);
            }
            if (this.sessionCacheSizeInBytes >= 0) {
                SSLSessionUtils.updateSessionCacheSize(baseSslContext, this.sessionCacheSizeInBytes);
            }
            SSLParameters baseSslParameters = this.createSslParameters(baseSslContext);
            FenixSSLContext sslContext = new FenixSSLContext(baseSslContext, baseSslParameters);
            HostnameVerifier hostnameVerifier = Optional.ofNullable(this.hostnameVerifierEnhancer).map(enhancer -> HostnameVerifierUtils.createEnhanceable(this.hostnameVerifier, enhancer)).orElse(this.hostnameVerifier);
            SSLMaterial sslMaterial = new SSLMaterial.Builder().withSslContext(sslContext).withKeyManager(keyManager).withTrustManager(trustManager).withSslParameters(baseSslParameters).withHostnameVerifier(hostnameVerifier).withCiphers(Collections.unmodifiableList(Arrays.asList(baseSslParameters.getCipherSuites()))).withProtocols(Collections.unmodifiableList(Arrays.asList(baseSslParameters.getProtocols()))).build();
            return new SSLFactory(sslMaterial);
        }

        private boolean isTrustMaterialPresent() {
            return !this.trustStores.isEmpty() || !this.trustManagers.isEmpty();
        }

        private boolean isIdentityMaterialPresent() {
            return !this.identities.isEmpty() || !this.identityManagers.isEmpty();
        }

        private X509ExtendedKeyManager createKeyManager() {
            return KeyManagerUtils.keyManagerBuilder().withKeyManagers(this.identityManagers).withIdentities(this.identities).withSwappableKeyManager(this.swappableKeyManagerEnabled).withLoggingKeyManager(this.loggingKeyManagerEnabled).withIdentityRoute(this.preferredAliasToHost).build();
        }

        private X509ExtendedTrustManager createTrustManager() {
            return TrustManagerUtils.trustManagerBuilder().withTrustManagers(this.trustManagers).withTrustStores(this.trustStores).withSwappableTrustManager(this.swappableTrustManagerEnabled).withLoggingTrustManager(this.loggingTrustManagerEnabled).withTrustEnhancer(this.trustManagerParametersValidator).withTrustEnhancer(this.chainAndAuthTypeValidator).withTrustEnhancer(this.chainAndAuthTypeWithSocketValidator).withTrustEnhancer(this.chainAndAuthTypeWithSSLEngineValidator).build();
        }

        private SSLParameters createSslParameters(SSLContext sslContext) {
            SSLParameters defaultSSLParameters = sslContext.getDefaultSSLParameters();
            List<String> defaultCiphers = Arrays.asList(defaultSSLParameters.getCipherSuites());
            List<String> defaultProtocols = Arrays.asList(defaultSSLParameters.getProtocols());
            String[] preferredCiphers = this.ciphers.stream().distinct().filter(StringUtils::isNotBlank).filter(defaultCiphers::contains).collect(CollectorsUtils.toStringArray());
            String[] preferredProtocols = this.protocols.stream().distinct().filter(StringUtils::isNotBlank).filter(defaultProtocols::contains).collect(CollectorsUtils.toStringArray());
            this.sslParameters.setCipherSuites(preferredCiphers);
            this.sslParameters.setProtocols(preferredProtocols);
            return SSLParametersUtils.merge(this.sslParameters, defaultSSLParameters);
        }

        private static interface QuadConsumer<T, U, V, W> {
            public void accept(T var1, U var2, V var3, W var4);
        }
    }
}

