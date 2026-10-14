/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.exception.GenericTrustManagerException;
import nl.altindag.ssl.model.TrustManagerParameters;
import nl.altindag.ssl.trustmanager.CertificateCapturingX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.CompositeX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.DummyX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.EnhanceableX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.HotSwappableX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.InflatableX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.LoggingX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.TrustManagerFactoryWrapper;
import nl.altindag.ssl.trustmanager.UnsafeX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.X509TrustManagerWrapper;
import nl.altindag.ssl.trustmanager.validator.ChainAndAuthTypeValidator;
import nl.altindag.ssl.trustmanager.validator.ChainAndAuthTypeWithSSLEngineValidator;
import nl.altindag.ssl.trustmanager.validator.ChainAndAuthTypeWithSocketValidator;
import nl.altindag.ssl.util.KeyStoreUtils;
import nl.altindag.ssl.util.internal.CollectorsUtils;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TrustManagerUtils {
    private TrustManagerUtils() {
    }

    public static X509ExtendedTrustManager combine(X509TrustManager ... trustManagers) {
        return TrustManagerUtils.combine(Arrays.asList(trustManagers));
    }

    public static X509ExtendedTrustManager combine(List<? extends X509TrustManager> trustManagers) {
        return TrustManagerUtils.trustManagerBuilder().withTrustManagers(trustManagers).build();
    }

    public static <T extends X509TrustManager> X509ExtendedTrustManager[] toArray(T trustManager) {
        return new X509ExtendedTrustManager[]{TrustManagerUtils.wrapIfNeeded(trustManager)};
    }

    public static X509ExtendedTrustManager createTrustManagerWithJdkTrustedCertificates() {
        return TrustManagerUtils.createTrustManager((KeyStore)null);
    }

    public static Optional<X509ExtendedTrustManager> createTrustManagerWithSystemTrustedCertificates() {
        List<KeyStore> trustStores = KeyStoreUtils.loadSystemKeyStores();
        if (trustStores.isEmpty()) {
            return Optional.empty();
        }
        X509ExtendedTrustManager trustManager = TrustManagerUtils.createTrustManager(trustStores.toArray(new KeyStore[0]));
        return Optional.of(trustManager);
    }

    public static X509ExtendedTrustManager createTrustManager(List<X509Certificate> certificates) {
        KeyStore trustStore = KeyStoreUtils.createTrustStore(certificates);
        return TrustManagerUtils.createTrustManager(trustStore);
    }

    public static X509ExtendedTrustManager createTrustManager(KeyStore ... trustStores) {
        return Arrays.stream(trustStores).map(TrustManagerUtils::createTrustManager).collect(CollectorsUtils.toListAndThen(TrustManagerUtils::combine));
    }

    public static X509ExtendedTrustManager createTrustManager(KeyStore trustStore) {
        return TrustManagerUtils.createTrustManager(trustStore, TrustManagerFactory.getDefaultAlgorithm());
    }

    public static X509ExtendedTrustManager createTrustManager(KeyStore trustStore, String trustManagerFactoryAlgorithm) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm);
            return TrustManagerUtils.createTrustManager(trustStore, trustManagerFactory);
        }
        catch (NoSuchAlgorithmException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createTrustManager(KeyStore trustStore, String trustManagerFactoryAlgorithm, String securityProviderName) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm, securityProviderName);
            return TrustManagerUtils.createTrustManager(trustStore, trustManagerFactory);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createTrustManager(KeyStore trustStore, String trustManagerFactoryAlgorithm, Provider securityProvider) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm, securityProvider);
            return TrustManagerUtils.createTrustManager(trustStore, trustManagerFactory);
        }
        catch (NoSuchAlgorithmException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createTrustManager(KeyStore trustStore, TrustManagerFactory trustManagerFactory) {
        try {
            trustManagerFactory.init(trustStore);
            return TrustManagerUtils.getTrustManager(trustManagerFactory);
        }
        catch (KeyStoreException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createTrustManager(ManagerFactoryParameters ... managerFactoryParameters) {
        return Arrays.stream(managerFactoryParameters).map(TrustManagerUtils::createTrustManager).collect(CollectorsUtils.toListAndThen(TrustManagerUtils::combine));
    }

    public static X509ExtendedTrustManager createTrustManager(ManagerFactoryParameters managerFactoryParameters) {
        return TrustManagerUtils.createTrustManager(managerFactoryParameters, TrustManagerFactory.getDefaultAlgorithm());
    }

    public static X509ExtendedTrustManager createTrustManager(ManagerFactoryParameters managerFactoryParameters, String trustManagerFactoryAlgorithm) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm);
            return TrustManagerUtils.createTrustManager(managerFactoryParameters, trustManagerFactory);
        }
        catch (NoSuchAlgorithmException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createTrustManager(ManagerFactoryParameters managerFactoryParameters, String trustManagerFactoryAlgorithm, String securityProviderName) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm, securityProviderName);
            return TrustManagerUtils.createTrustManager(managerFactoryParameters, trustManagerFactory);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createTrustManager(ManagerFactoryParameters managerFactoryParameters, String trustManagerFactoryAlgorithm, Provider securityProvider) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm, securityProvider);
            return TrustManagerUtils.createTrustManager(managerFactoryParameters, trustManagerFactory);
        }
        catch (NoSuchAlgorithmException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createTrustManager(ManagerFactoryParameters managerFactoryParameters, TrustManagerFactory trustManagerFactory) {
        try {
            trustManagerFactory.init(managerFactoryParameters);
            return TrustManagerUtils.getTrustManager(trustManagerFactory);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw new GenericTrustManagerException(e);
        }
    }

    public static X509ExtendedTrustManager createUnsafeTrustManager() {
        return UnsafeX509ExtendedTrustManager.getInstance();
    }

    public static X509ExtendedTrustManager createDummyTrustManager() {
        return DummyX509ExtendedTrustManager.getInstance();
    }

    public static X509ExtendedTrustManager createLoggingTrustManager(X509TrustManager baseTrustManager) {
        return new LoggingX509ExtendedTrustManager(TrustManagerUtils.wrapIfNeeded(baseTrustManager));
    }

    public static X509ExtendedTrustManager createCertificateCapturingTrustManager(List<X509Certificate> certificatesCollector) {
        return TrustManagerUtils.createCertificateCapturingTrustManager(TrustManagerUtils.createUnsafeTrustManager(), certificatesCollector);
    }

    public static X509ExtendedTrustManager createCertificateCapturingTrustManager(X509TrustManager baseTrustManager, List<X509Certificate> certificatesCollector) {
        return new CertificateCapturingX509ExtendedTrustManager(TrustManagerUtils.wrapIfNeeded(baseTrustManager), certificatesCollector);
    }

    public static X509ExtendedTrustManager wrapIfNeeded(X509TrustManager trustManager) {
        if (trustManager instanceof X509ExtendedTrustManager) {
            return (X509ExtendedTrustManager)trustManager;
        }
        return new X509TrustManagerWrapper(trustManager);
    }

    public static TrustManagerFactory createTrustManagerFactory(TrustManager trustManager) {
        return new TrustManagerFactoryWrapper(trustManager);
    }

    public static <T extends TrustManagerFactory> X509ExtendedTrustManager getTrustManager(T trustManagerFactory) {
        return Arrays.stream(trustManagerFactory.getTrustManagers()).filter(X509TrustManager.class::isInstance).map(X509TrustManager.class::cast).map(TrustManagerUtils::wrapIfNeeded).collect(Collectors.collectingAndThen(Collectors.toList(), TrustManagerUtils::combine));
    }

    public static X509ExtendedTrustManager createInflatableTrustManager() {
        return new InflatableX509ExtendedTrustManager();
    }

    @Deprecated
    public static X509ExtendedTrustManager createInflatableTrustManager(Path trustStorePath, char[] trustStorePassword, String trustStoreType, BiPredicate<X509Certificate[], String> certificateAndAuthTypeTrustPredicate) {
        return new InflatableX509ExtendedTrustManager(trustStorePath, trustStorePassword, trustStoreType, trustManagerParameters -> certificateAndAuthTypeTrustPredicate.test(trustManagerParameters.getChain(), trustManagerParameters.getAuthType()));
    }

    public static X509ExtendedTrustManager createInflatableTrustManager(Path trustStorePath, char[] trustStorePassword, String trustStoreType, Predicate<TrustManagerParameters> trustManagerParametersPredicate) {
        return new InflatableX509ExtendedTrustManager(trustStorePath, trustStorePassword, trustStoreType, trustManagerParametersPredicate);
    }

    public static void addCertificate(X509ExtendedTrustManager trustManager, X509Certificate certificate) {
        TrustManagerUtils.addCertificate(trustManager, Collections.singletonList(certificate));
    }

    public static void addCertificate(X509ExtendedTrustManager trustManager, List<X509Certificate> certificates) {
        if (trustManager instanceof InflatableX509ExtendedTrustManager) {
            ((InflatableX509ExtendedTrustManager)trustManager).addCertificates(certificates);
            return;
        }
        if (trustManager instanceof HotSwappableX509ExtendedTrustManager && ((HotSwappableX509ExtendedTrustManager)trustManager).getInnerTrustManager() instanceof InflatableX509ExtendedTrustManager) {
            ((InflatableX509ExtendedTrustManager)((HotSwappableX509ExtendedTrustManager)trustManager).getInnerTrustManager()).addCertificates(certificates);
            return;
        }
        if (trustManager instanceof CompositeX509ExtendedTrustManager) {
            Optional<InflatableX509ExtendedTrustManager> inflatableX509ExtendedTrustManager = ((CompositeX509ExtendedTrustManager)trustManager).getInnerTrustManagers().stream().filter(InflatableX509ExtendedTrustManager.class::isInstance).map(InflatableX509ExtendedTrustManager.class::cast).findFirst();
            if (inflatableX509ExtendedTrustManager.isPresent()) {
                inflatableX509ExtendedTrustManager.get().addCertificates(certificates);
                return;
            }
        }
        throw new GenericTrustManagerException(String.format("The provided trustManager should be an instance of [%s]", InflatableX509ExtendedTrustManager.class.getName()));
    }

    public static X509ExtendedTrustManager createSwappableTrustManager(X509TrustManager trustManager) {
        return new HotSwappableX509ExtendedTrustManager(TrustManagerUtils.wrapIfNeeded(trustManager));
    }

    public static void swapTrustManager(X509TrustManager baseTrustManager, X509TrustManager newTrustManager) {
        if (baseTrustManager instanceof InflatableX509ExtendedTrustManager) {
            throw new GenericTrustManagerException(String.format("The baseTrustManager is from the instance of [%s] and should be an instance of [%s].", baseTrustManager.getClass().getName(), HotSwappableX509ExtendedTrustManager.class.getName()));
        }
        if (newTrustManager instanceof HotSwappableX509ExtendedTrustManager && !(newTrustManager instanceof InflatableX509ExtendedTrustManager)) {
            throw new GenericTrustManagerException(String.format("The newTrustManager should not be an instance of [%s]", HotSwappableX509ExtendedTrustManager.class.getName()));
        }
        if (baseTrustManager instanceof HotSwappableX509ExtendedTrustManager && ((HotSwappableX509ExtendedTrustManager)baseTrustManager).getInnerTrustManager() instanceof LoggingX509ExtendedTrustManager) {
            ((HotSwappableX509ExtendedTrustManager)baseTrustManager).setTrustManager(new LoggingX509ExtendedTrustManager(TrustManagerUtils.wrapIfNeeded(newTrustManager)));
        } else if (baseTrustManager instanceof HotSwappableX509ExtendedTrustManager) {
            ((HotSwappableX509ExtendedTrustManager)baseTrustManager).setTrustManager(TrustManagerUtils.wrapIfNeeded(newTrustManager));
        } else {
            throw new GenericTrustManagerException(String.format("The baseTrustManager is from the instance of [%s] and should be an instance of [%s].", baseTrustManager.getClass().getName(), HotSwappableX509ExtendedTrustManager.class.getName()));
        }
    }

    @Deprecated
    public static X509ExtendedTrustManager createEnhanceableTrustManager(X509ExtendedTrustManager trustManager, ChainAndAuthTypeValidator chainAndAuthTypeValidator, ChainAndAuthTypeWithSocketValidator chainAndAuthTypeWithSocketValidator, ChainAndAuthTypeWithSSLEngineValidator chainAndAuthTypeWithSSLEngineValidator) {
        Predicate<TrustManagerParameters> trustManagerParametersValidator = chainAndAuthTypeValidator == null && chainAndAuthTypeWithSocketValidator == null && chainAndAuthTypeWithSSLEngineValidator == null ? null : trustManagerParameters -> {
            boolean result = false;
            if (chainAndAuthTypeValidator != null && !trustManagerParameters.getSocket().isPresent() && !trustManagerParameters.getSslEngine().isPresent()) {
                result = chainAndAuthTypeValidator.test(trustManagerParameters.getChain(), trustManagerParameters.getAuthType());
            }
            if (chainAndAuthTypeWithSocketValidator != null && trustManagerParameters.getSocket().isPresent()) {
                result = chainAndAuthTypeWithSocketValidator.test(trustManagerParameters.getChain(), trustManagerParameters.getAuthType(), trustManagerParameters.getSocket().get());
            }
            if (chainAndAuthTypeWithSSLEngineValidator != null && trustManagerParameters.getSslEngine().isPresent()) {
                result = chainAndAuthTypeWithSSLEngineValidator.test(trustManagerParameters.getChain(), trustManagerParameters.getAuthType(), trustManagerParameters.getSslEngine().get());
            }
            return result;
        };
        return new EnhanceableX509ExtendedTrustManager(trustManager, trustManagerParametersValidator);
    }

    public static X509ExtendedTrustManager createEnhanceableTrustManager(X509ExtendedTrustManager trustManager, Predicate<TrustManagerParameters> trustManagerParametersValidator) {
        return new EnhanceableX509ExtendedTrustManager(trustManager, trustManagerParametersValidator);
    }

    private static List<X509ExtendedTrustManager> unwrapIfPossible(X509ExtendedTrustManager trustManager) {
        if (trustManager instanceof CompositeX509ExtendedTrustManager) {
            ArrayList<X509ExtendedTrustManager> trustManagers = new ArrayList<X509ExtendedTrustManager>();
            for (X509ExtendedTrustManager innerTrustManager : ((CompositeX509ExtendedTrustManager)trustManager).getInnerTrustManagers()) {
                List<X509ExtendedTrustManager> unwrappedTrustManagers = TrustManagerUtils.unwrapIfPossible(innerTrustManager);
                trustManagers.addAll(unwrappedTrustManagers);
            }
            return trustManagers;
        }
        return Collections.singletonList(trustManager);
    }

    public static TrustManagerBuilder trustManagerBuilder() {
        return new TrustManagerBuilder();
    }

    public static final class TrustManagerBuilder {
        private static final Logger LOGGER = LoggerFactory.getLogger(TrustManagerBuilder.class);
        private static final String EMPTY_TRUST_MANAGER_EXCEPTION = "Input does not contain TrustManager";
        private final List<X509ExtendedTrustManager> trustManagers = new ArrayList<X509ExtendedTrustManager>();
        private boolean swappableTrustManagerEnabled = false;
        private boolean loggingTrustManagerEnabled = false;
        private ChainAndAuthTypeValidator chainAndAuthTypeValidator;
        private ChainAndAuthTypeWithSocketValidator chainAndAuthTypeWithSocketValidator;
        private ChainAndAuthTypeWithSSLEngineValidator chainAndAuthTypeWithSSLEngineValidator;
        private Predicate<TrustManagerParameters> trustManagerParametersValidator;

        private TrustManagerBuilder() {
        }

        public <T extends X509TrustManager> TrustManagerBuilder withTrustManagers(T ... trustManagers) {
            for (T trustManager : trustManagers) {
                this.withTrustManager(trustManager);
            }
            return this;
        }

        public <T extends X509TrustManager> TrustManagerBuilder withTrustManagers(List<T> trustManagers) {
            for (X509TrustManager trustManager : trustManagers) {
                this.withTrustManager(trustManager);
            }
            return this;
        }

        public <T extends X509TrustManager> TrustManagerBuilder withTrustManager(T trustManager) {
            this.trustManagers.add(TrustManagerUtils.wrapIfNeeded(trustManager));
            return this;
        }

        public <T extends KeyStore> TrustManagerBuilder withTrustStores(T ... trustStores) {
            return this.withTrustStores(Arrays.asList(trustStores));
        }

        public TrustManagerBuilder withTrustStores(List<? extends KeyStore> trustStores) {
            for (KeyStore keyStore : trustStores) {
                this.trustManagers.add(TrustManagerUtils.createTrustManager(keyStore));
            }
            return this;
        }

        public <T extends KeyStore> TrustManagerBuilder withTrustStore(T trustStore) {
            this.trustManagers.add(TrustManagerUtils.createTrustManager(trustStore));
            return this;
        }

        public <T extends KeyStore> TrustManagerBuilder withTrustStore(T trustStore, String trustManagerAlgorithm) {
            this.trustManagers.add(TrustManagerUtils.createTrustManager(trustStore, trustManagerAlgorithm));
            return this;
        }

        public TrustManagerBuilder withSwappableTrustManager(boolean swappableTrustManagerEnabled) {
            this.swappableTrustManagerEnabled = swappableTrustManagerEnabled;
            return this;
        }

        public TrustManagerBuilder withLoggingTrustManager(boolean loggingTrustManagerEnabled) {
            this.loggingTrustManagerEnabled = loggingTrustManagerEnabled;
            return this;
        }

        @Deprecated
        public TrustManagerBuilder withTrustEnhancer(ChainAndAuthTypeValidator validator) {
            this.chainAndAuthTypeValidator = validator;
            return this;
        }

        @Deprecated
        public TrustManagerBuilder withTrustEnhancer(ChainAndAuthTypeWithSocketValidator validator) {
            this.chainAndAuthTypeWithSocketValidator = validator;
            return this;
        }

        @Deprecated
        public TrustManagerBuilder withTrustEnhancer(ChainAndAuthTypeWithSSLEngineValidator validator) {
            this.chainAndAuthTypeWithSSLEngineValidator = validator;
            return this;
        }

        public TrustManagerBuilder withTrustEnhancer(Predicate<TrustManagerParameters> trustManagerParametersValidator) {
            this.trustManagerParametersValidator = trustManagerParametersValidator;
            return this;
        }

        public X509ExtendedTrustManager build() {
            X509ExtendedTrustManager baseTrustManager;
            ValidationUtils.requireNotEmpty(this.trustManagers, () -> new GenericTrustManagerException(EMPTY_TRUST_MANAGER_EXCEPTION));
            Optional<X509ExtendedTrustManager> unsafeOrDummyTrustManager = this.getUnsafeOrDummyTrustManagerIfConfigured(this.trustManagers);
            if (unsafeOrDummyTrustManager.isPresent()) {
                baseTrustManager = unsafeOrDummyTrustManager.get();
            } else {
                baseTrustManager = this.combine(this.trustManagers);
                baseTrustManager = this.createEnhanceableTrustManagerIfEnabled(baseTrustManager).orElse(baseTrustManager);
            }
            if (this.loggingTrustManagerEnabled) {
                baseTrustManager = TrustManagerUtils.createLoggingTrustManager(baseTrustManager);
            }
            if (this.swappableTrustManagerEnabled) {
                baseTrustManager = TrustManagerUtils.createSwappableTrustManager(baseTrustManager);
            }
            return baseTrustManager;
        }

        private Optional<X509ExtendedTrustManager> getUnsafeOrDummyTrustManagerIfConfigured(List<X509ExtendedTrustManager> trustManagers) {
            Optional<X509ExtendedTrustManager> maybeUnsafeTrustManager = trustManagers.stream().filter(UnsafeX509ExtendedTrustManager.class::isInstance).findAny();
            if (maybeUnsafeTrustManager.isPresent()) {
                if (trustManagers.size() > 1) {
                    LOGGER.debug("Unsafe TrustManager is being used therefore other trust managers will not be included for constructing the base trust manager");
                }
                return maybeUnsafeTrustManager;
            }
            Optional<X509ExtendedTrustManager> maybeDummyTrustManager = trustManagers.stream().filter(DummyX509ExtendedTrustManager.class::isInstance).findAny();
            if (maybeDummyTrustManager.isPresent()) {
                if (trustManagers.size() > 1) {
                    LOGGER.debug("Dummy TrustManager is being used therefore other trust managers will not be included for constructing the base trust manager");
                }
                return maybeDummyTrustManager;
            }
            return Optional.empty();
        }

        private X509ExtendedTrustManager combine(List<X509ExtendedTrustManager> trustManagers) {
            if (trustManagers.size() == 1) {
                return trustManagers.get(0);
            }
            return trustManagers.stream().map(x$0 -> TrustManagerUtils.unwrapIfPossible(x$0)).flatMap(Collection::stream).collect(CollectorsUtils.toListAndThen(CompositeX509ExtendedTrustManager::new));
        }

        private Optional<X509ExtendedTrustManager> createEnhanceableTrustManagerIfEnabled(X509ExtendedTrustManager baseTrustManager) {
            if (this.chainAndAuthTypeValidator == null && this.chainAndAuthTypeWithSocketValidator == null && this.chainAndAuthTypeWithSSLEngineValidator == null && this.trustManagerParametersValidator == null) {
                return Optional.empty();
            }
            X509ExtendedTrustManager enhanceableTrustManager = this.trustManagerParametersValidator != null ? TrustManagerUtils.createEnhanceableTrustManager(baseTrustManager, this.trustManagerParametersValidator) : (this.chainAndAuthTypeValidator != null ? TrustManagerUtils.createEnhanceableTrustManager(baseTrustManager, trustManagerParameters -> this.chainAndAuthTypeValidator.test(trustManagerParameters.getChain(), trustManagerParameters.getAuthType())) : (this.chainAndAuthTypeWithSocketValidator != null ? TrustManagerUtils.createEnhanceableTrustManager(baseTrustManager, trustManagerParameters -> this.chainAndAuthTypeWithSocketValidator.test(trustManagerParameters.getChain(), trustManagerParameters.getAuthType(), trustManagerParameters.getSocket().orElse(null))) : TrustManagerUtils.createEnhanceableTrustManager(baseTrustManager, trustManagerParameters -> this.chainAndAuthTypeWithSSLEngineValidator.test(trustManagerParameters.getChain(), trustManagerParameters.getAuthType(), trustManagerParameters.getSslEngine().orElse(null)))));
            return Optional.ofNullable(enhanceableTrustManager);
        }
    }
}

