/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.AlgorithmConstraints;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.AbstractSessionContext;
import org.conscrypt.ApplicationProtocolSelectorAdapter;
import org.conscrypt.ClientSessionContext;
import org.conscrypt.DuckTypedPSKKeyManager;
import org.conscrypt.EmptyArray;
import org.conscrypt.NativeCrypto;
import org.conscrypt.PSKKeyManager;
import org.conscrypt.Platform;
import org.conscrypt.SSLUtils;
import org.conscrypt.ServerSessionContext;

final class SSLParametersImpl
implements Cloneable {
    private static volatile X509KeyManager defaultX509KeyManager;
    private static volatile X509TrustManager defaultX509TrustManager;
    private static volatile SSLParametersImpl defaultParameters;
    private final ClientSessionContext clientSessionContext;
    private final ServerSessionContext serverSessionContext;
    private final X509KeyManager x509KeyManager;
    private final PSKKeyManager pskKeyManager;
    private final X509TrustManager x509TrustManager;
    String[] enabledProtocols;
    boolean isEnabledProtocolsFiltered;
    String[] enabledCipherSuites;
    private boolean client_mode = true;
    private boolean need_client_auth = false;
    private boolean want_client_auth = false;
    private boolean enable_session_creation = true;
    private String endpointIdentificationAlgorithm;
    private boolean useCipherSuitesOrder;
    private Collection<SNIMatcher> sniMatchers;
    private AlgorithmConstraints algorithmConstraints;
    private boolean ctVerificationEnabled;
    byte[] sctExtension;
    byte[] ocspResponse;
    byte[] applicationProtocols = EmptyArray.BYTE;
    ApplicationProtocolSelectorAdapter applicationProtocolSelector;
    boolean useSessionTickets;
    private Boolean useSni;
    boolean channelIdEnabled;
    private static final String[] EMPTY_STRING_ARRAY;

    SSLParametersImpl(KeyManager[] kms, TrustManager[] tms, SecureRandom sr, ClientSessionContext clientSessionContext, ServerSessionContext serverSessionContext, String[] protocols) throws KeyManagementException {
        this.serverSessionContext = serverSessionContext;
        this.clientSessionContext = clientSessionContext;
        if (kms == null) {
            this.x509KeyManager = SSLParametersImpl.getDefaultX509KeyManager();
            this.pskKeyManager = null;
        } else {
            this.x509KeyManager = SSLParametersImpl.findFirstX509KeyManager(kms);
            this.pskKeyManager = SSLParametersImpl.findFirstPSKKeyManager(kms);
        }
        this.x509TrustManager = tms == null ? SSLParametersImpl.getDefaultX509TrustManager() : SSLParametersImpl.findFirstX509TrustManager(tms);
        this.enabledProtocols = (String[])NativeCrypto.checkEnabledProtocols(protocols == null ? NativeCrypto.DEFAULT_PROTOCOLS : protocols).clone();
        boolean x509CipherSuitesNeeded = this.x509KeyManager != null || this.x509TrustManager != null;
        boolean pskCipherSuitesNeeded = this.pskKeyManager != null;
        this.enabledCipherSuites = SSLParametersImpl.getDefaultCipherSuites(x509CipherSuitesNeeded, pskCipherSuitesNeeded);
    }

    private SSLParametersImpl(ClientSessionContext clientSessionContext, ServerSessionContext serverSessionContext, X509KeyManager x509KeyManager, PSKKeyManager pskKeyManager, X509TrustManager x509TrustManager, SSLParametersImpl sslParams) {
        this.clientSessionContext = clientSessionContext;
        this.serverSessionContext = serverSessionContext;
        this.x509KeyManager = x509KeyManager;
        this.pskKeyManager = pskKeyManager;
        this.x509TrustManager = x509TrustManager;
        this.enabledProtocols = sslParams.enabledProtocols == null ? null : (String[])sslParams.enabledProtocols.clone();
        this.isEnabledProtocolsFiltered = sslParams.isEnabledProtocolsFiltered;
        this.enabledCipherSuites = sslParams.enabledCipherSuites == null ? null : (String[])sslParams.enabledCipherSuites.clone();
        this.client_mode = sslParams.client_mode;
        this.need_client_auth = sslParams.need_client_auth;
        this.want_client_auth = sslParams.want_client_auth;
        this.enable_session_creation = sslParams.enable_session_creation;
        this.endpointIdentificationAlgorithm = sslParams.endpointIdentificationAlgorithm;
        this.useCipherSuitesOrder = sslParams.useCipherSuitesOrder;
        this.ctVerificationEnabled = sslParams.ctVerificationEnabled;
        this.sctExtension = sslParams.sctExtension == null ? null : (byte[])sslParams.sctExtension.clone();
        this.ocspResponse = sslParams.ocspResponse == null ? null : (byte[])sslParams.ocspResponse.clone();
        this.applicationProtocols = sslParams.applicationProtocols == null ? null : (byte[])sslParams.applicationProtocols.clone();
        this.applicationProtocolSelector = sslParams.applicationProtocolSelector;
        this.useSessionTickets = sslParams.useSessionTickets;
        this.useSni = sslParams.useSni;
        this.channelIdEnabled = sslParams.channelIdEnabled;
    }

    static SSLParametersImpl getDefault() throws KeyManagementException {
        SSLParametersImpl result = defaultParameters;
        if (result == null) {
            defaultParameters = result = new SSLParametersImpl(null, null, null, new ClientSessionContext(), new ServerSessionContext(), null);
        }
        return (SSLParametersImpl)result.clone();
    }

    AbstractSessionContext getSessionContext() {
        return this.client_mode ? this.clientSessionContext : this.serverSessionContext;
    }

    ClientSessionContext getClientSessionContext() {
        return this.clientSessionContext;
    }

    X509KeyManager getX509KeyManager() {
        return this.x509KeyManager;
    }

    PSKKeyManager getPSKKeyManager() {
        return this.pskKeyManager;
    }

    X509TrustManager getX509TrustManager() {
        return this.x509TrustManager;
    }

    String[] getEnabledCipherSuites() {
        if (Arrays.asList(this.enabledProtocols).contains("TLSv1.3")) {
            return SSLUtils.concat(NativeCrypto.SUPPORTED_TLS_1_3_CIPHER_SUITES, this.enabledCipherSuites);
        }
        return (String[])this.enabledCipherSuites.clone();
    }

    void setEnabledCipherSuites(String[] cipherSuites) {
        this.enabledCipherSuites = NativeCrypto.checkEnabledCipherSuites(SSLParametersImpl.filterFromCipherSuites(cipherSuites, NativeCrypto.SUPPORTED_TLS_1_3_CIPHER_SUITES_SET));
    }

    String[] getEnabledProtocols() {
        return (String[])this.enabledProtocols.clone();
    }

    void setEnabledProtocols(String[] protocols) {
        if (protocols == null) {
            throw new IllegalArgumentException("protocols == null");
        }
        String[] filteredProtocols = SSLParametersImpl.filterFromProtocols(protocols, "SSLv3");
        this.isEnabledProtocolsFiltered = protocols.length != filteredProtocols.length;
        this.enabledProtocols = (String[])NativeCrypto.checkEnabledProtocols(filteredProtocols).clone();
    }

    void setApplicationProtocols(String[] protocols) {
        this.applicationProtocols = SSLUtils.encodeProtocols(protocols);
    }

    String[] getApplicationProtocols() {
        return SSLUtils.decodeProtocols(this.applicationProtocols);
    }

    void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelector) {
        this.applicationProtocolSelector = applicationProtocolSelector;
    }

    ApplicationProtocolSelectorAdapter getApplicationProtocolSelector() {
        return this.applicationProtocolSelector;
    }

    void setUseClientMode(boolean mode) {
        this.client_mode = mode;
    }

    boolean getUseClientMode() {
        return this.client_mode;
    }

    void setNeedClientAuth(boolean need) {
        this.need_client_auth = need;
        this.want_client_auth = false;
    }

    boolean getNeedClientAuth() {
        return this.need_client_auth;
    }

    void setWantClientAuth(boolean want) {
        this.want_client_auth = want;
        this.need_client_auth = false;
    }

    boolean getWantClientAuth() {
        return this.want_client_auth;
    }

    void setEnableSessionCreation(boolean flag) {
        this.enable_session_creation = flag;
    }

    boolean getEnableSessionCreation() {
        return this.enable_session_creation;
    }

    void setUseSessionTickets(boolean useSessionTickets) {
        this.useSessionTickets = useSessionTickets;
    }

    void setUseSni(boolean flag) {
        this.useSni = flag;
    }

    boolean getUseSni() {
        return this.useSni != null ? this.useSni.booleanValue() : this.isSniEnabledByDefault();
    }

    void setCTVerificationEnabled(boolean enabled) {
        this.ctVerificationEnabled = enabled;
    }

    void setSCTExtension(byte[] extension) {
        this.sctExtension = extension;
    }

    void setOCSPResponse(byte[] response) {
        this.ocspResponse = response;
    }

    byte[] getOCSPResponse() {
        return this.ocspResponse;
    }

    private static String[] filterFromProtocols(String[] protocols, String obsoleteProtocol) {
        if (protocols.length == 1 && obsoleteProtocol.equals(protocols[0])) {
            return EMPTY_STRING_ARRAY;
        }
        ArrayList<String> newProtocols = new ArrayList<String>();
        for (String protocol : protocols) {
            if (obsoleteProtocol.equals(protocol)) continue;
            newProtocols.add(protocol);
        }
        return newProtocols.toArray(EMPTY_STRING_ARRAY);
    }

    private static String[] filterFromCipherSuites(String[] cipherSuites, Set<String> toRemove) {
        if (cipherSuites == null || cipherSuites.length == 0) {
            return cipherSuites;
        }
        ArrayList<String> newCipherSuites = new ArrayList<String>(cipherSuites.length);
        for (String cipherSuite : cipherSuites) {
            if (toRemove.contains(cipherSuite)) continue;
            newCipherSuites.add(cipherSuite);
        }
        return newCipherSuites.toArray(EMPTY_STRING_ARRAY);
    }

    private boolean isSniEnabledByDefault() {
        try {
            String enableSNI = System.getProperty("jsse.enableSNIExtension", "true");
            if ("true".equalsIgnoreCase(enableSNI)) {
                return true;
            }
            if ("false".equalsIgnoreCase(enableSNI)) {
                return false;
            }
            throw new RuntimeException("Can only set \"jsse.enableSNIExtension\" to \"true\" or \"false\"");
        }
        catch (SecurityException e) {
            return true;
        }
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError((Object)e);
        }
    }

    SSLParametersImpl cloneWithTrustManager(X509TrustManager newTrustManager) {
        return new SSLParametersImpl(this.clientSessionContext, this.serverSessionContext, this.x509KeyManager, this.pskKeyManager, newTrustManager, this);
    }

    private static X509KeyManager getDefaultX509KeyManager() throws KeyManagementException {
        X509KeyManager result = defaultX509KeyManager;
        if (result == null) {
            defaultX509KeyManager = result = SSLParametersImpl.createDefaultX509KeyManager();
        }
        return result;
    }

    private static X509KeyManager createDefaultX509KeyManager() throws KeyManagementException {
        try {
            String algorithm = KeyManagerFactory.getDefaultAlgorithm();
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(null, null);
            Object[] kms = kmf.getKeyManagers();
            X509KeyManager result = SSLParametersImpl.findFirstX509KeyManager((KeyManager[])kms);
            if (result == null) {
                throw new KeyManagementException("No X509KeyManager among default KeyManagers: " + Arrays.toString(kms));
            }
            return result;
        }
        catch (NoSuchAlgorithmException e) {
            throw new KeyManagementException(e);
        }
        catch (KeyStoreException e) {
            throw new KeyManagementException(e);
        }
        catch (UnrecoverableKeyException e) {
            throw new KeyManagementException(e);
        }
    }

    private static X509KeyManager findFirstX509KeyManager(KeyManager[] kms) {
        for (KeyManager km4 : kms) {
            if (!(km4 instanceof X509KeyManager)) continue;
            return (X509KeyManager)km4;
        }
        return null;
    }

    private static PSKKeyManager findFirstPSKKeyManager(KeyManager[] kms) {
        for (KeyManager km4 : kms) {
            if (km4 instanceof PSKKeyManager) {
                return (PSKKeyManager)km4;
            }
            if (km4 == null) continue;
            try {
                return DuckTypedPSKKeyManager.getInstance(km4);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
        }
        return null;
    }

    static X509TrustManager getDefaultX509TrustManager() throws KeyManagementException {
        X509TrustManager result = defaultX509TrustManager;
        if (result == null) {
            defaultX509TrustManager = result = SSLParametersImpl.createDefaultX509TrustManager();
        }
        return result;
    }

    private static X509TrustManager createDefaultX509TrustManager() throws KeyManagementException {
        try {
            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
            tmf.init((KeyStore)null);
            Object[] tms = tmf.getTrustManagers();
            X509TrustManager trustManager = SSLParametersImpl.findFirstX509TrustManager((TrustManager[])tms);
            if (trustManager == null) {
                throw new KeyManagementException("No X509TrustManager in among default TrustManagers: " + Arrays.toString(tms));
            }
            return trustManager;
        }
        catch (NoSuchAlgorithmException e) {
            throw new KeyManagementException(e);
        }
        catch (KeyStoreException e) {
            throw new KeyManagementException(e);
        }
    }

    private static X509TrustManager findFirstX509TrustManager(TrustManager[] tms) {
        for (TrustManager tm : tms) {
            if (!(tm instanceof X509TrustManager)) continue;
            return (X509TrustManager)tm;
        }
        return null;
    }

    String getEndpointIdentificationAlgorithm() {
        return this.endpointIdentificationAlgorithm;
    }

    void setEndpointIdentificationAlgorithm(String endpointIdentificationAlgorithm) {
        this.endpointIdentificationAlgorithm = endpointIdentificationAlgorithm;
    }

    boolean getUseCipherSuitesOrder() {
        return this.useCipherSuitesOrder;
    }

    Collection<SNIMatcher> getSNIMatchers() {
        if (this.sniMatchers == null) {
            return null;
        }
        return new ArrayList<SNIMatcher>(this.sniMatchers);
    }

    void setSNIMatchers(Collection<SNIMatcher> sniMatchers) {
        this.sniMatchers = sniMatchers != null ? new ArrayList<SNIMatcher>(sniMatchers) : null;
    }

    AlgorithmConstraints getAlgorithmConstraints() {
        return this.algorithmConstraints;
    }

    void setAlgorithmConstraints(AlgorithmConstraints algorithmConstraints) {
        this.algorithmConstraints = algorithmConstraints;
    }

    void setUseCipherSuitesOrder(boolean useCipherSuitesOrder) {
        this.useCipherSuitesOrder = useCipherSuitesOrder;
    }

    private static String[] getDefaultCipherSuites(boolean x509CipherSuitesNeeded, boolean pskCipherSuitesNeeded) {
        if (x509CipherSuitesNeeded) {
            if (pskCipherSuitesNeeded) {
                return SSLUtils.concat(NativeCrypto.DEFAULT_PSK_CIPHER_SUITES, NativeCrypto.DEFAULT_X509_CIPHER_SUITES, {"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"});
            }
            return SSLUtils.concat(NativeCrypto.DEFAULT_X509_CIPHER_SUITES, {"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"});
        }
        if (pskCipherSuitesNeeded) {
            return SSLUtils.concat(NativeCrypto.DEFAULT_PSK_CIPHER_SUITES, {"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"});
        }
        return new String[]{"TLS_EMPTY_RENEGOTIATION_INFO_SCSV"};
    }

    boolean isCTVerificationEnabled(String hostname) {
        if (hostname == null) {
            return false;
        }
        if (this.ctVerificationEnabled) {
            return true;
        }
        return Platform.isCTVerificationRequired(hostname);
    }

    static {
        EMPTY_STRING_ARRAY = new String[0];
    }

    static interface PSKCallbacks {
        public String chooseServerPSKIdentityHint(PSKKeyManager var1);

        public String chooseClientPSKIdentity(PSKKeyManager var1, String var2);

        public SecretKey getPSKKey(PSKKeyManager var1, String var2, String var3);
    }

    static interface AliasChooser {
        public String chooseClientAlias(X509KeyManager var1, X500Principal[] var2, String[] var3);

        public String chooseServerAlias(X509KeyManager var1, String var2);
    }
}

