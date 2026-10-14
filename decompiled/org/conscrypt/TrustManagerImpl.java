/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;
import org.conscrypt.CertBlocklist;
import org.conscrypt.CertPinManager;
import org.conscrypt.CertificatePriorityComparator;
import org.conscrypt.ChainStrengthAnalyzer;
import org.conscrypt.ConscryptCertStore;
import org.conscrypt.ConscryptHostnameVerifier;
import org.conscrypt.ConscryptSession;
import org.conscrypt.Platform;
import org.conscrypt.TrustedCertificateIndex;
import org.conscrypt.ct.CTLogStore;
import org.conscrypt.ct.CTPolicy;
import org.conscrypt.ct.CTVerificationResult;
import org.conscrypt.ct.CTVerifier;

public final class TrustManagerImpl
extends X509ExtendedTrustManager {
    private static final Logger logger = Logger.getLogger(TrustManagerImpl.class.getName());
    private static final TrustAnchorComparator TRUST_ANCHOR_COMPARATOR = new TrustAnchorComparator();
    private static ConscryptHostnameVerifier defaultHostnameVerifier;
    private final KeyStore rootKeyStore;
    private CertPinManager pinManager;
    private final ConscryptCertStore trustedCertificateStore;
    private final CertPathValidator validator;
    private final TrustedCertificateIndex trustedCertificateIndex;
    private final TrustedCertificateIndex intermediateIndex;
    private final X509Certificate[] acceptedIssuers;
    private final Exception err;
    private final CertificateFactory factory;
    private final CertBlocklist blocklist;
    private CTVerifier ctVerifier;
    private CTPolicy ctPolicy;
    private ConscryptHostnameVerifier hostnameVerifier;
    private boolean ctEnabledOverride;

    public TrustManagerImpl(KeyStore keyStore) {
        this(keyStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager manager) {
        this(keyStore, manager, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager manager, ConscryptCertStore certStore) {
        this(keyStore, manager, certStore, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager manager, ConscryptCertStore certStore, CertBlocklist blocklist) {
        this(keyStore, manager, certStore, blocklist, null, null, null);
    }

    public TrustManagerImpl(KeyStore keyStore, CertPinManager manager, ConscryptCertStore certStore, CertBlocklist blocklist, CTLogStore ctLogStore, CTVerifier ctVerifier, CTPolicy ctPolicy) {
        CertPathValidator validatorLocal = null;
        CertificateFactory factoryLocal = null;
        KeyStore rootKeyStoreLocal = null;
        ConscryptCertStore trustedCertificateStoreLocal = null;
        TrustedCertificateIndex trustedCertificateIndexLocal = null;
        X509Certificate[] acceptedIssuersLocal = null;
        Exception errLocal = null;
        try {
            validatorLocal = CertPathValidator.getInstance("PKIX");
            factoryLocal = CertificateFactory.getInstance("X509");
            if ("AndroidCAStore".equals(keyStore.getType()) && Platform.supportsConscryptCertStore()) {
                rootKeyStoreLocal = keyStore;
                trustedCertificateStoreLocal = certStore != null ? certStore : Platform.newDefaultCertStore();
                acceptedIssuersLocal = null;
                trustedCertificateIndexLocal = new TrustedCertificateIndex();
            } else {
                rootKeyStoreLocal = null;
                trustedCertificateStoreLocal = certStore;
                acceptedIssuersLocal = TrustManagerImpl.acceptedIssuers(keyStore);
                trustedCertificateIndexLocal = new TrustedCertificateIndex(TrustManagerImpl.trustAnchors(acceptedIssuersLocal));
            }
        }
        catch (Exception e) {
            errLocal = e;
        }
        if (blocklist == null) {
            blocklist = Platform.newDefaultBlocklist();
        }
        if (ctLogStore == null) {
            ctLogStore = Platform.newDefaultLogStore();
        }
        if (ctPolicy == null) {
            ctPolicy = Platform.newDefaultPolicy(ctLogStore);
        }
        this.pinManager = manager;
        this.rootKeyStore = rootKeyStoreLocal;
        this.trustedCertificateStore = trustedCertificateStoreLocal;
        this.validator = validatorLocal;
        this.factory = factoryLocal;
        this.trustedCertificateIndex = trustedCertificateIndexLocal;
        this.intermediateIndex = new TrustedCertificateIndex();
        this.acceptedIssuers = acceptedIssuersLocal;
        this.err = errLocal;
        this.blocklist = blocklist;
        this.ctVerifier = new CTVerifier(ctLogStore);
        this.ctPolicy = ctPolicy;
    }

    private static X509Certificate[] acceptedIssuers(KeyStore ks) {
        try {
            ArrayList<X509Certificate> trusted = new ArrayList<X509Certificate>();
            Enumeration<String> en = ks.aliases();
            while (en.hasMoreElements()) {
                String alias = en.nextElement();
                X509Certificate cert = (X509Certificate)ks.getCertificate(alias);
                if (cert == null) continue;
                trusted.add(cert);
            }
            return trusted.toArray(new X509Certificate[trusted.size()]);
        }
        catch (KeyStoreException e) {
            return new X509Certificate[0];
        }
    }

    private static Set<TrustAnchor> trustAnchors(X509Certificate[] certs) {
        HashSet<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>(certs.length);
        for (X509Certificate cert : certs) {
            trustAnchors.add(new TrustAnchor(cert, null));
        }
        return trustAnchors;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(chain, authType, null, null, true);
    }

    public List<X509Certificate> checkClientTrusted(X509Certificate[] chain, String authType, String hostname) throws CertificateException {
        return this.checkTrusted(chain, null, null, authType, hostname, true);
    }

    private static SSLSession getHandshakeSessionOrThrow(SSLSocket sslSocket) throws CertificateException {
        SSLSession session = sslSocket.getHandshakeSession();
        if (session == null) {
            throw new CertificateException("Not in handshake; no session available");
        }
        return session;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        SSLSession session = null;
        SSLParameters parameters = null;
        if (socket instanceof SSLSocket) {
            SSLSocket sslSocket = (SSLSocket)socket;
            session = TrustManagerImpl.getHandshakeSessionOrThrow(sslSocket);
            parameters = sslSocket.getSSLParameters();
        }
        this.checkTrusted(chain, authType, session, parameters, true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        SSLSession session = engine.getHandshakeSession();
        if (session == null) {
            throw new CertificateException("Not in handshake; no session available");
        }
        this.checkTrusted(chain, authType, session, engine.getSSLParameters(), true);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(chain, authType, null, null, false);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] chain, String authType, String hostname) throws CertificateException {
        return this.checkTrusted(chain, null, null, authType, hostname, false);
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] certs, String authType, Socket socket) throws CertificateException {
        SSLSession session = null;
        SSLParameters parameters = null;
        if (socket instanceof SSLSocket) {
            SSLSocket sslSocket = (SSLSocket)socket;
            session = TrustManagerImpl.getHandshakeSessionOrThrow(sslSocket);
            parameters = sslSocket.getSSLParameters();
        }
        return this.checkTrusted(certs, authType, session, parameters, false);
    }

    public List<X509Certificate> getTrustedChainForServer(X509Certificate[] certs, String authType, SSLEngine engine) throws CertificateException {
        SSLSession session = engine.getHandshakeSession();
        if (session == null) {
            throw new CertificateException("Not in handshake; no session available");
        }
        return this.checkTrusted(certs, authType, session, engine.getSSLParameters(), false);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.getTrustedChainForServer(chain, authType, socket);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        this.getTrustedChainForServer(chain, authType, engine);
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] chain, String authType, SSLSession session) throws CertificateException {
        return this.checkTrusted(chain, authType, session, null, false);
    }

    public void handleTrustStorageUpdate() {
        if (this.acceptedIssuers == null) {
            this.trustedCertificateIndex.reset();
        } else {
            this.trustedCertificateIndex.reset(TrustManagerImpl.trustAnchors(this.acceptedIssuers));
        }
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] certs, String authType, SSLSession session, SSLParameters parameters, boolean clientAuth) throws CertificateException {
        ConscryptHostnameVerifier verifier;
        String identificationAlgorithm;
        byte[] ocspData = null;
        byte[] tlsSctData = null;
        String hostname = null;
        if (session != null) {
            hostname = session.getPeerHost();
            ocspData = TrustManagerImpl.getOcspDataFromSession(session);
            tlsSctData = this.getTlsSctDataFromSession(session);
        }
        if (session != null && parameters != null && "HTTPS".equalsIgnoreCase(identificationAlgorithm = parameters.getEndpointIdentificationAlgorithm()) && !(verifier = this.getHttpsVerifier()).verify(certs, hostname, session)) {
            throw new CertificateException("No subjectAltNames on the certificate match");
        }
        return this.checkTrusted(certs, ocspData, tlsSctData, authType, hostname, clientAuth);
    }

    private static byte[] getOcspDataFromSession(SSLSession session) {
        List ocspResponses = null;
        if (session instanceof ConscryptSession) {
            ConscryptSession opensslSession = (ConscryptSession)session;
            ocspResponses = opensslSession.getStatusResponses();
        } else {
            try {
                Method m_getResponses = session.getClass().getDeclaredMethod("getStatusResponses", new Class[0]);
                m_getResponses.setAccessible(true);
                Object rawResponses = m_getResponses.invoke((Object)session, new Object[0]);
                if (rawResponses instanceof List) {
                    ocspResponses = (List)rawResponses;
                }
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException rawResponses) {
            }
            catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        if (ocspResponses == null || ocspResponses.isEmpty()) {
            return null;
        }
        return ocspResponses.get(0);
    }

    private byte[] getTlsSctDataFromSession(SSLSession session) {
        if (session instanceof ConscryptSession) {
            ConscryptSession opensslSession = (ConscryptSession)session;
            return opensslSession.getPeerSignedCertificateTimestamp();
        }
        byte[] data = null;
        try {
            Method m_getTlsSctData = session.getClass().getDeclaredMethod("getPeerSignedCertificateTimestamp", new Class[0]);
            m_getTlsSctData.setAccessible(true);
            Object rawData = m_getTlsSctData.invoke((Object)session, new Object[0]);
            if (rawData instanceof byte[]) {
                data = (byte[])rawData;
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException m_getTlsSctData) {
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
        return data;
    }

    private List<X509Certificate> checkTrusted(X509Certificate[] certs, byte[] ocspData, byte[] tlsSctData, String authType, String host2, boolean clientAuth) throws CertificateException {
        if (certs == null || certs.length == 0 || authType == null || authType.length() == 0) {
            throw new IllegalArgumentException("null or zero-length parameter");
        }
        if (this.err != null) {
            throw new CertificateException(this.err);
        }
        HashSet<X509Certificate> used = new HashSet<X509Certificate>();
        ArrayList<X509Certificate> untrustedChain = new ArrayList<X509Certificate>();
        ArrayList<TrustAnchor> trustedChain = new ArrayList<TrustAnchor>();
        X509Certificate leaf = certs[0];
        TrustAnchor leafAsAnchor = this.findTrustAnchorBySubjectAndPublicKey(leaf);
        if (leafAsAnchor != null) {
            trustedChain.add(leafAsAnchor);
            used.add(leafAsAnchor.getTrustedCert());
        } else {
            untrustedChain.add(leaf);
        }
        used.add(leaf);
        return this.checkTrustedRecursive(certs, ocspData, tlsSctData, host2, clientAuth, untrustedChain, trustedChain, used);
    }

    private List<X509Certificate> checkTrustedRecursive(X509Certificate[] certs, byte[] ocspData, byte[] tlsSctData, String host2, boolean clientAuth, ArrayList<X509Certificate> untrustedChain, ArrayList<TrustAnchor> trustAnchorChain, Set<X509Certificate> used) throws CertificateException {
        CertificateException lastException = null;
        X509Certificate current = trustAnchorChain.isEmpty() ? untrustedChain.get(untrustedChain.size() - 1) : trustAnchorChain.get(trustAnchorChain.size() - 1).getTrustedCert();
        this.checkBlocklist(current);
        if (current.getIssuerDN().equals(current.getSubjectDN())) {
            return this.verifyChain(untrustedChain, trustAnchorChain, host2, clientAuth, ocspData, tlsSctData);
        }
        Set<TrustAnchor> anchors = this.findAllTrustAnchorsByIssuerAndSignature(current);
        boolean seenIssuer = false;
        for (TrustAnchor anchor : TrustManagerImpl.sortPotentialAnchors(anchors)) {
            X509Certificate anchorCert = anchor.getTrustedCert();
            if (used.contains(anchorCert)) continue;
            seenIssuer = true;
            used.add(anchorCert);
            trustAnchorChain.add(anchor);
            try {
                return this.checkTrustedRecursive(certs, ocspData, tlsSctData, host2, clientAuth, untrustedChain, trustAnchorChain, used);
            }
            catch (CertificateException ex) {
                lastException = ex;
                trustAnchorChain.remove(trustAnchorChain.size() - 1);
                used.remove(anchorCert);
            }
        }
        if (!trustAnchorChain.isEmpty()) {
            if (!seenIssuer) {
                return this.verifyChain(untrustedChain, trustAnchorChain, host2, clientAuth, ocspData, tlsSctData);
            }
            throw lastException;
        }
        for (int i = 1; i < certs.length; ++i) {
            X509Certificate candidateIssuer = certs[i];
            if (used.contains(candidateIssuer) || !current.getIssuerDN().equals(candidateIssuer.getSubjectDN())) continue;
            try {
                candidateIssuer.checkValidity();
                ChainStrengthAnalyzer.checkCert(candidateIssuer);
            }
            catch (CertificateException ex) {
                lastException = new CertificateException("Unacceptable certificate: " + candidateIssuer.getSubjectX500Principal(), ex);
                continue;
            }
            used.add(candidateIssuer);
            untrustedChain.add(candidateIssuer);
            try {
                return this.checkTrustedRecursive(certs, ocspData, tlsSctData, host2, clientAuth, untrustedChain, trustAnchorChain, used);
            }
            catch (CertificateException ex) {
                lastException = ex;
                used.remove(candidateIssuer);
                untrustedChain.remove(untrustedChain.size() - 1);
            }
        }
        Set<TrustAnchor> intermediateAnchors = this.intermediateIndex.findAllByIssuerAndSignature(current);
        for (TrustAnchor intermediate : TrustManagerImpl.sortPotentialAnchors(intermediateAnchors)) {
            X509Certificate intermediateCert = intermediate.getTrustedCert();
            if (used.contains(intermediateCert)) continue;
            used.add(intermediateCert);
            untrustedChain.add(intermediateCert);
            try {
                return this.checkTrustedRecursive(certs, ocspData, tlsSctData, host2, clientAuth, untrustedChain, trustAnchorChain, used);
            }
            catch (CertificateException ex) {
                lastException = ex;
                untrustedChain.remove(untrustedChain.size() - 1);
                used.remove(intermediateCert);
            }
        }
        if (lastException != null) {
            throw lastException;
        }
        CertPath certPath = this.factory.generateCertPath(untrustedChain);
        throw new CertificateException(new CertPathValidatorException("Trust anchor for certification path not found.", null, certPath, -1));
    }

    private List<X509Certificate> verifyChain(List<X509Certificate> untrustedChain, List<TrustAnchor> trustAnchorChain, String host2, boolean clientAuth, byte[] ocspData, byte[] tlsSctData) throws CertificateException {
        try {
            CertPath certPath = this.factory.generateCertPath(untrustedChain);
            if (trustAnchorChain.isEmpty()) {
                throw new CertificateException(new CertPathValidatorException("Trust anchor for certification path not found.", null, certPath, -1));
            }
            ArrayList<X509Certificate> wholeChain = new ArrayList<X509Certificate>();
            wholeChain.addAll(untrustedChain);
            for (TrustAnchor anchor : trustAnchorChain) {
                wholeChain.add(anchor.getTrustedCert());
            }
            if (this.pinManager != null) {
                this.pinManager.checkChainPinning(host2, wholeChain);
            }
            for (X509Certificate cert : wholeChain) {
                this.checkBlocklist(cert);
            }
            if (!clientAuth && (this.ctEnabledOverride || host2 != null && Platform.isCTVerificationRequired(host2))) {
                this.checkCT(host2, wholeChain, ocspData, tlsSctData);
            }
            if (untrustedChain.isEmpty()) {
                return wholeChain;
            }
            ChainStrengthAnalyzer.check(untrustedChain);
            try {
                HashSet<TrustAnchor> anchorSet = new HashSet<TrustAnchor>();
                anchorSet.add(trustAnchorChain.get(0));
                PKIXParameters params = new PKIXParameters(anchorSet);
                params.setRevocationEnabled(false);
                X509Certificate endPointCert = untrustedChain.get(0);
                this.setOcspResponses(params, endPointCert, ocspData);
                params.addCertPathChecker(new ExtendedKeyUsagePKIXCertPathChecker(clientAuth, endPointCert));
                this.validator.validate(certPath, params);
            }
            catch (InvalidAlgorithmParameterException e) {
                throw new CertificateException("Chain validation failed", e);
            }
            catch (CertPathValidatorException e) {
                throw new CertificateException("Chain validation failed", e);
            }
            for (int i = 1; i < untrustedChain.size(); ++i) {
                this.intermediateIndex.index(untrustedChain.get(i));
            }
            return wholeChain;
        }
        catch (CertificateException e) {
            logger.fine("Rejected candidate cert chain due to error: " + e.getMessage());
            throw e;
        }
    }

    private void checkBlocklist(X509Certificate cert) throws CertificateException {
        if (this.blocklist != null && this.blocklist.isPublicKeyBlockListed(cert.getPublicKey())) {
            throw new CertificateException("Certificate blocklisted by public key: " + cert);
        }
    }

    private void checkCT(String host2, List<X509Certificate> chain, byte[] ocspData, byte[] tlsData) throws CertificateException {
        CTVerificationResult result = this.ctVerifier.verifySignedCertificateTimestamps(chain, tlsData, ocspData);
        if (!this.ctPolicy.doesResultConformToPolicy(result, host2, chain.toArray(new X509Certificate[chain.size()]))) {
            throw new CertificateException("Certificate chain does not conform to required transparency policy.");
        }
    }

    private void setOcspResponses(PKIXParameters params, X509Certificate cert, byte[] ocspData) {
        if (ocspData == null) {
            return;
        }
        PKIXRevocationChecker revChecker = null;
        ArrayList<PKIXCertPathChecker> checkers = new ArrayList<PKIXCertPathChecker>(params.getCertPathCheckers());
        for (PKIXCertPathChecker checker : checkers) {
            if (!(checker instanceof PKIXRevocationChecker)) continue;
            revChecker = (PKIXRevocationChecker)checker;
            break;
        }
        if (revChecker == null) {
            try {
                revChecker = (PKIXRevocationChecker)this.validator.getRevocationChecker();
            }
            catch (UnsupportedOperationException e) {
                return;
            }
            checkers.add(revChecker);
            revChecker.setOptions(Collections.singleton(PKIXRevocationChecker.Option.ONLY_END_ENTITY));
        }
        revChecker.setOcspResponses(Collections.singletonMap(cert, ocspData));
        params.setCertPathCheckers(checkers);
    }

    private static Collection<TrustAnchor> sortPotentialAnchors(Set<TrustAnchor> anchors) {
        if (anchors.size() <= 1) {
            return anchors;
        }
        ArrayList<TrustAnchor> sortedAnchors = new ArrayList<TrustAnchor>(anchors);
        Collections.sort(sortedAnchors, TRUST_ANCHOR_COMPARATOR);
        return sortedAnchors;
    }

    private Set<TrustAnchor> findAllTrustAnchorsByIssuerAndSignature(X509Certificate cert) {
        Set<TrustAnchor> indexedAnchors = this.trustedCertificateIndex.findAllByIssuerAndSignature(cert);
        if (!indexedAnchors.isEmpty() || this.trustedCertificateStore == null) {
            return indexedAnchors;
        }
        Set<X509Certificate> storeAnchors = this.trustedCertificateStore.findAllIssuers(cert);
        if (storeAnchors.isEmpty()) {
            return indexedAnchors;
        }
        HashSet<TrustAnchor> result = new HashSet<TrustAnchor>(storeAnchors.size());
        for (X509Certificate storeCert : storeAnchors) {
            result.add(this.trustedCertificateIndex.index(storeCert));
        }
        return result;
    }

    private TrustAnchor findTrustAnchorBySubjectAndPublicKey(X509Certificate cert) {
        TrustAnchor trustAnchor = this.trustedCertificateIndex.findBySubjectAndPublicKey(cert);
        if (trustAnchor != null) {
            return trustAnchor;
        }
        if (this.trustedCertificateStore == null) {
            return null;
        }
        X509Certificate systemCert = this.trustedCertificateStore.getTrustAnchor(cert);
        if (systemCert != null) {
            return new TrustAnchor(systemCert, null);
        }
        return null;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.acceptedIssuers != null ? (X509Certificate[])this.acceptedIssuers.clone() : TrustManagerImpl.acceptedIssuers(this.rootKeyStore);
    }

    static synchronized void setDefaultHostnameVerifier(ConscryptHostnameVerifier verifier) {
        defaultHostnameVerifier = verifier;
    }

    static synchronized ConscryptHostnameVerifier getDefaultHostnameVerifier() {
        return defaultHostnameVerifier;
    }

    void setHostnameVerifier(ConscryptHostnameVerifier verifier) {
        this.hostnameVerifier = verifier;
    }

    ConscryptHostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    private ConscryptHostnameVerifier getHttpsVerifier() {
        if (this.hostnameVerifier != null) {
            return this.hostnameVerifier;
        }
        return Platform.getDefaultHostnameVerifier();
    }

    public void setCTEnabledOverride(boolean enabled) {
        this.ctEnabledOverride = enabled;
    }

    public void setCTVerifier(CTVerifier verifier) {
        this.ctVerifier = verifier;
    }

    public void setCTPolicy(CTPolicy policy) {
        this.ctPolicy = policy;
    }

    private static class ExtendedKeyUsagePKIXCertPathChecker
    extends PKIXCertPathChecker {
        private static final String EKU_OID = "2.5.29.37";
        private static final String EKU_anyExtendedKeyUsage = "2.5.29.37.0";
        private static final String EKU_clientAuth = "1.3.6.1.5.5.7.3.2";
        private static final String EKU_serverAuth = "1.3.6.1.5.5.7.3.1";
        private static final String EKU_nsSGC = "2.16.840.1.113730.4.1";
        private static final String EKU_msSGC = "1.3.6.1.4.1.311.10.3.3";
        private static final Set<String> SUPPORTED_EXTENSIONS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("2.5.29.37")));
        private final boolean clientAuth;
        private final X509Certificate leaf;

        private ExtendedKeyUsagePKIXCertPathChecker(boolean clientAuth, X509Certificate leaf) {
            this.clientAuth = clientAuth;
            this.leaf = leaf;
        }

        @Override
        public void init(boolean forward) throws CertPathValidatorException {
        }

        @Override
        public boolean isForwardCheckingSupported() {
            return true;
        }

        @Override
        public Set<String> getSupportedExtensions() {
            return SUPPORTED_EXTENSIONS;
        }

        @Override
        public void check(Certificate c, Collection<String> unresolvedCritExts) throws CertPathValidatorException {
            List<String> ekuOids;
            if (c != this.leaf) {
                return;
            }
            try {
                ekuOids = this.leaf.getExtendedKeyUsage();
            }
            catch (CertificateParsingException e) {
                throw new CertPathValidatorException(e);
            }
            if (ekuOids == null) {
                return;
            }
            boolean goodExtendedKeyUsage = false;
            for (String ekuOid : ekuOids) {
                if (ekuOid.equals(EKU_anyExtendedKeyUsage)) {
                    goodExtendedKeyUsage = true;
                    break;
                }
                if (this.clientAuth) {
                    if (!ekuOid.equals(EKU_clientAuth)) continue;
                    goodExtendedKeyUsage = true;
                    break;
                }
                if (ekuOid.equals(EKU_serverAuth)) {
                    goodExtendedKeyUsage = true;
                    break;
                }
                if (ekuOid.equals(EKU_nsSGC)) {
                    goodExtendedKeyUsage = true;
                    break;
                }
                if (!ekuOid.equals(EKU_msSGC)) continue;
                goodExtendedKeyUsage = true;
                break;
            }
            if (!goodExtendedKeyUsage) {
                throw new CertPathValidatorException("End-entity certificate does not have a valid extendedKeyUsage.");
            }
            unresolvedCritExts.remove(EKU_OID);
        }
    }

    private static class TrustAnchorComparator
    implements Comparator<TrustAnchor> {
        private static final CertificatePriorityComparator CERT_COMPARATOR = new CertificatePriorityComparator();

        private TrustAnchorComparator() {
        }

        @Override
        public int compare(TrustAnchor lhs, TrustAnchor rhs) {
            X509Certificate lhsCert = lhs.getTrustedCert();
            X509Certificate rhsCert = rhs.getTrustedCert();
            return CERT_COMPARATOR.compare(lhsCert, rhsCert);
        }
    }
}

