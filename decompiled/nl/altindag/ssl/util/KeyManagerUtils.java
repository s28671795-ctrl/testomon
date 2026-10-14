/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.net.URI;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import nl.altindag.ssl.exception.GenericKeyManagerException;
import nl.altindag.ssl.exception.GenericKeyStoreException;
import nl.altindag.ssl.keymanager.CompositeX509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.DummyX509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.HotSwappableX509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.KeyManagerFactoryWrapper;
import nl.altindag.ssl.keymanager.LoggingX509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.X509KeyManagerWrapper;
import nl.altindag.ssl.model.KeyStoreHolder;
import nl.altindag.ssl.util.CertificateUtils;
import nl.altindag.ssl.util.KeyStoreUtils;
import nl.altindag.ssl.util.internal.CollectorsUtils;
import nl.altindag.ssl.util.internal.UriUtils;
import nl.altindag.ssl.util.internal.ValidationUtils;

public final class KeyManagerUtils {
    private static final char[] DUMMY_PASSWORD = "dummy-password".toCharArray();

    private KeyManagerUtils() {
    }

    public static X509ExtendedKeyManager combine(X509KeyManager ... keyManagers) {
        return KeyManagerUtils.combine(Arrays.asList(keyManagers));
    }

    public static X509ExtendedKeyManager combine(List<? extends X509KeyManager> keyManagers) {
        return KeyManagerUtils.keyManagerBuilder().withKeyManagers(keyManagers).build();
    }

    public static <T extends X509KeyManager> X509ExtendedKeyManager[] toArray(T keyManager) {
        return new X509ExtendedKeyManager[]{KeyManagerUtils.wrapIfNeeded(keyManager)};
    }

    public static X509ExtendedKeyManager createKeyManager(KeyStoreHolder ... keyStoreHolders) {
        return Arrays.stream(keyStoreHolders).map(keyStoreHolder -> KeyManagerUtils.createKeyManager(keyStoreHolder.getKeyStore(), keyStoreHolder.getKeyPassword())).collect(CollectorsUtils.toListAndThen(KeyManagerUtils::combine));
    }

    public static X509ExtendedKeyManager createKeyManager(KeyStore keyStore, char[] keyPassword) {
        return KeyManagerUtils.createKeyManager(keyStore, keyPassword, KeyManagerFactory.getDefaultAlgorithm());
    }

    public static X509ExtendedKeyManager createKeyManager(KeyStore keyStore, char[] keyPassword, String keyManagerFactoryAlgorithm) {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keyManagerFactoryAlgorithm);
            return KeyManagerUtils.createKeyManager(keyStore, keyPassword, keyManagerFactory);
        }
        catch (NoSuchAlgorithmException e) {
            throw new GenericKeyManagerException(e);
        }
    }

    public static X509ExtendedKeyManager createKeyManager(KeyStore keyStore, char[] keyPassword, String keyManagerFactoryAlgorithm, String securityProviderName) {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keyManagerFactoryAlgorithm, securityProviderName);
            return KeyManagerUtils.createKeyManager(keyStore, keyPassword, keyManagerFactory);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new GenericKeyManagerException(e);
        }
    }

    public static X509ExtendedKeyManager createKeyManager(KeyStore keyStore, char[] keyPassword, String keyManagerFactoryAlgorithm, Provider securityProvider) {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keyManagerFactoryAlgorithm, securityProvider);
            return KeyManagerUtils.createKeyManager(keyStore, keyPassword, keyManagerFactory);
        }
        catch (NoSuchAlgorithmException e) {
            throw new GenericKeyManagerException(e);
        }
    }

    public static X509ExtendedKeyManager createKeyManager(KeyStore keyStore, char[] keyPassword, KeyManagerFactory keyManagerFactory) {
        try {
            keyManagerFactory.init(keyStore, keyPassword);
            return KeyManagerUtils.getKeyManager(keyManagerFactory);
        }
        catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new GenericKeyManagerException(e);
        }
    }

    public static X509ExtendedKeyManager createKeyManager(KeyStore keyStore, Map<String, char[]> aliasToPassword) {
        ArrayList<X509ExtendedKeyManager> keyManagers = new ArrayList<X509ExtendedKeyManager>();
        for (Map.Entry<String, char[]> entry : aliasToPassword.entrySet()) {
            try {
                String alias = entry.getKey();
                char[] password = entry.getValue();
                if (!keyStore.isKeyEntry(alias)) continue;
                Key key = keyStore.getKey(alias, password);
                Certificate[] certificateChain = keyStore.getCertificateChain(alias);
                KeyStore identityStore = KeyStoreUtils.createIdentityStore((Key)key, (char[])password, (Certificate[])certificateChain);
                X509ExtendedKeyManager keyManager = KeyManagerUtils.createKeyManager(identityStore, password);
                keyManagers.add(keyManager);
            }
            catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
                throw new GenericKeyManagerException(e);
            }
        }
        ValidationUtils.requireNotEmpty(keyManagers, () -> new GenericKeyManagerException("Could not create any KeyManager from the given KeyStore, Alias and Password"));
        return KeyManagerUtils.combine(keyManagers);
    }

    public static X509ExtendedKeyManager wrapIfNeeded(X509KeyManager keyManager) {
        if (keyManager instanceof X509ExtendedKeyManager) {
            return (X509ExtendedKeyManager)keyManager;
        }
        return new X509KeyManagerWrapper(keyManager);
    }

    public static KeyManagerFactory createKeyManagerFactory(KeyManager keyManager) {
        return new KeyManagerFactoryWrapper(keyManager);
    }

    public static <T extends KeyManagerFactory> X509ExtendedKeyManager getKeyManager(T keyManagerFactory) {
        return Arrays.stream(keyManagerFactory.getKeyManagers()).filter(X509KeyManager.class::isInstance).map(X509KeyManager.class::cast).map(KeyManagerUtils::wrapIfNeeded).collect(CollectorsUtils.toListAndThen(KeyManagerUtils::combine));
    }

    public static X509ExtendedKeyManager createDummyKeyManager() {
        return DummyX509ExtendedKeyManager.getInstance();
    }

    public static X509ExtendedKeyManager createLoggingKeyManager(X509KeyManager keyManager) {
        return new LoggingX509ExtendedKeyManager(KeyManagerUtils.wrapIfNeeded(keyManager));
    }

    public static X509ExtendedKeyManager createSwappableKeyManager(X509KeyManager keyManager) {
        return new HotSwappableX509ExtendedKeyManager(KeyManagerUtils.wrapIfNeeded(keyManager));
    }

    public static void swapKeyManager(X509KeyManager baseKeyManager, X509KeyManager newKeyManager) {
        if (newKeyManager instanceof HotSwappableX509ExtendedKeyManager) {
            throw new GenericKeyManagerException(String.format("The newKeyManager should not be an instance of [%s]", HotSwappableX509ExtendedKeyManager.class.getName()));
        }
        if (baseKeyManager instanceof HotSwappableX509ExtendedKeyManager && ((HotSwappableX509ExtendedKeyManager)baseKeyManager).getInnerKeyManager() instanceof LoggingX509ExtendedKeyManager) {
            ((HotSwappableX509ExtendedKeyManager)baseKeyManager).setKeyManager(new LoggingX509ExtendedKeyManager(KeyManagerUtils.wrapIfNeeded(newKeyManager)));
        } else if (baseKeyManager instanceof HotSwappableX509ExtendedKeyManager) {
            ((HotSwappableX509ExtendedKeyManager)baseKeyManager).setKeyManager(KeyManagerUtils.wrapIfNeeded(newKeyManager));
        } else {
            throw new GenericKeyManagerException(String.format("The baseKeyManager is from the instance of [%s] and should be an instance of [%s].", baseKeyManager.getClass().getName(), HotSwappableX509ExtendedKeyManager.class.getName()));
        }
    }

    public static void addIdentityRoute(X509ExtendedKeyManager keyManager, String alias, String ... hosts) {
        KeyManagerUtils.addIdentityRoute(keyManager, alias, hosts, false);
    }

    public static void overrideIdentityRoute(X509ExtendedKeyManager keyManager, String alias, String ... hosts) {
        KeyManagerUtils.addIdentityRoute(keyManager, alias, hosts, true);
    }

    private static void addIdentityRoute(X509ExtendedKeyManager keyManager, String alias, String[] hosts, boolean overrideExistingRouteEnabled) {
        ValidationUtils.requireNotNull(keyManager, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("KeyManager"));
        ValidationUtils.requireNotNull(alias, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("Alias"));
        ValidationUtils.requireNotNull(keyManager, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("Host"));
        if (keyManager instanceof CompositeX509ExtendedKeyManager) {
            CompositeX509ExtendedKeyManager compositeX509ExtendedKeyManager = (CompositeX509ExtendedKeyManager)keyManager;
            Map<String, List<URI>> aliasToHosts = compositeX509ExtendedKeyManager.getIdentityRoute();
            ArrayList<URI> uris = new ArrayList<URI>();
            for (String host2 : hosts) {
                URI uri = URI.create(host2);
                UriUtils.validate(uri);
                uris.add(uri);
            }
            if (overrideExistingRouteEnabled && aliasToHosts.containsKey(alias)) {
                aliasToHosts.get(alias).clear();
            }
            for (URI uri : uris) {
                if (aliasToHosts.containsKey(alias)) {
                    aliasToHosts.get(alias).add(uri);
                    continue;
                }
                aliasToHosts.put(alias, new ArrayList<URI>(Collections.singleton(uri)));
            }
        } else {
            throw new GenericKeyManagerException(String.format("KeyManager should be an instance of: [%s], but received: [%s]", CompositeX509ExtendedKeyManager.class.getName(), keyManager.getClass().getName()));
        }
    }

    public static Map<String, List<String>> getIdentityRoute(X509ExtendedKeyManager keyManager) {
        ValidationUtils.requireNotNull(keyManager, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("KeyManager"));
        if (keyManager instanceof CompositeX509ExtendedKeyManager) {
            return ((CompositeX509ExtendedKeyManager)keyManager).getIdentityRoute().entrySet().stream().collect(Collectors.collectingAndThen(Collectors.toMap(Map.Entry::getKey, hosts -> ((List)hosts.getValue()).stream().map(URI::toString).collect(CollectorsUtils.toUnmodifiableList())), Collections::unmodifiableMap));
        }
        throw new GenericKeyManagerException(String.format("KeyManager should be an instance of: [%s], but received: [%s]", CompositeX509ExtendedKeyManager.class.getName(), keyManager.getClass().getName()));
    }

    private static List<X509ExtendedKeyManager> unwrapIfPossible(X509ExtendedKeyManager keyManager) {
        if (keyManager instanceof CompositeX509ExtendedKeyManager) {
            ArrayList<X509ExtendedKeyManager> keyManagers = new ArrayList<X509ExtendedKeyManager>();
            for (X509ExtendedKeyManager innerKeyManager : ((CompositeX509ExtendedKeyManager)keyManager).getInnerKeyManagers()) {
                List<X509ExtendedKeyManager> unwrappedKeyManagers = KeyManagerUtils.unwrapIfPossible(innerKeyManager);
                keyManagers.addAll(unwrappedKeyManagers);
            }
            return keyManagers;
        }
        return Collections.singletonList(keyManager);
    }

    public static KeyManagerBuilder keyManagerBuilder() {
        return new KeyManagerBuilder();
    }

    public static X509ExtendedKeyManager createKeyManager(PrivateKey privateKey, Certificate[] certificatesChain) {
        String alias = CertificateUtils.generateAlias(certificatesChain[0]);
        return KeyManagerUtils.createKeyManager(alias, privateKey, certificatesChain);
    }

    public static X509ExtendedKeyManager createKeyManager(String alias, PrivateKey privateKey, Certificate[] certificatesChain) {
        try {
            KeyStore keyStore = KeyStoreUtils.createKeyStore();
            keyStore.setKeyEntry(alias, privateKey, DUMMY_PASSWORD, certificatesChain);
            return KeyManagerUtils.createKeyManager(keyStore, DUMMY_PASSWORD);
        }
        catch (KeyStoreException e) {
            throw new GenericKeyStoreException(e);
        }
    }

    public static final class KeyManagerBuilder {
        private static final String EMPTY_KEY_MANAGER_EXCEPTION = "Input does not contain KeyManagers";
        private final List<X509ExtendedKeyManager> keyManagers = new ArrayList<X509ExtendedKeyManager>();
        private final Map<String, List<URI>> aliasToHost = new HashMap<String, List<URI>>();
        private boolean swappableKeyManagerEnabled = false;
        private boolean loggingKeyManagerEnabled = false;

        private KeyManagerBuilder() {
        }

        @SafeVarargs
        public final <T extends X509KeyManager> KeyManagerBuilder withKeyManagers(T ... keyManagers) {
            for (T keyManager : keyManagers) {
                this.withKeyManager(keyManager);
            }
            return this;
        }

        public <T extends X509KeyManager> KeyManagerBuilder withKeyManagers(List<T> keyManagers) {
            for (X509KeyManager keyManager : keyManagers) {
                this.withKeyManager(keyManager);
            }
            return this;
        }

        public <T extends X509KeyManager> KeyManagerBuilder withKeyManager(T keyManager) {
            this.keyManagers.add(KeyManagerUtils.wrapIfNeeded(keyManager));
            return this;
        }

        public KeyManagerBuilder withIdentities(KeyStoreHolder ... identities) {
            return this.withIdentities(Arrays.asList(identities));
        }

        public KeyManagerBuilder withIdentities(List<KeyStoreHolder> identities) {
            for (KeyStoreHolder identity : identities) {
                this.keyManagers.add(KeyManagerUtils.createKeyManager(identity.getKeyStore(), identity.getKeyPassword()));
            }
            return this;
        }

        public <T extends KeyStore> KeyManagerBuilder withIdentity(T identity, char[] identityPassword, String keyManagerAlgorithm) {
            this.keyManagers.add(KeyManagerUtils.createKeyManager(identity, identityPassword, keyManagerAlgorithm));
            return this;
        }

        public KeyManagerBuilder withSwappableKeyManager(boolean swappableKeyManagerEnabled) {
            this.swappableKeyManagerEnabled = swappableKeyManagerEnabled;
            return this;
        }

        public KeyManagerBuilder withLoggingKeyManager(boolean loggingKeyManagerEnabled) {
            this.loggingKeyManagerEnabled = loggingKeyManagerEnabled;
            return this;
        }

        public KeyManagerBuilder withIdentityRoute(Map<String, List<URI>> aliasToHost) {
            this.aliasToHost.putAll(aliasToHost);
            return this;
        }

        public X509ExtendedKeyManager build() {
            ValidationUtils.requireNotEmpty(this.keyManagers, () -> new GenericKeyManagerException(EMPTY_KEY_MANAGER_EXCEPTION));
            X509ExtendedKeyManager baseKeyManager = this.keyManagers.size() == 1 ? this.keyManagers.get(0) : (X509ExtendedKeyManager)this.keyManagers.stream().map(x$0 -> KeyManagerUtils.unwrapIfPossible(x$0)).flatMap(Collection::stream).collect(CollectorsUtils.toListAndThen(extendedKeyManagers -> new CompositeX509ExtendedKeyManager((List<? extends X509ExtendedKeyManager>)extendedKeyManagers, this.aliasToHost)));
            if (this.loggingKeyManagerEnabled) {
                baseKeyManager = KeyManagerUtils.createLoggingKeyManager(baseKeyManager);
            }
            if (this.swappableKeyManagerEnabled) {
                baseKeyManager = KeyManagerUtils.createSwappableKeyManager(baseKeyManager);
            }
            return baseKeyManager;
        }
    }
}

