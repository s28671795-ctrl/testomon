/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.osgi;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.jsse.TLSParameterJaxBUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.configuration.security.CertStoreType;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.CombinatorType;
import org.apache.cxf.configuration.security.DNConstraintsType;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.KeyStoreType;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TrustManagersType;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;
import org.apache.cxf.transport.https.InsecureTrustManager;
import org.apache.cxf.transports.http.configuration.ConnectionType;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.ProxyServerType;

class HttpConduitConfigApplier {
    private static final String SECURE_HTTP_PREFIX = "https";

    HttpConduitConfigApplier() {
    }

    void apply(Dictionary<String, String> d, HTTPConduit c, String address) {
        this.applyClientPolicies(d, c);
        this.applyAuthorization(d, c);
        this.applyProxyAuthorization(d, c);
        if (address != null && address.startsWith(SECURE_HTTP_PREFIX)) {
            this.applyTlsClientParameters(d, c);
        }
        this.applyAuthSupplier(d, c);
    }

    private void applyTlsClientParameters(Dictionary<String, String> d, HTTPConduit c) {
        Enumeration<String> keys2 = d.keys();
        TLSClientParameters p = c.getTlsClientParameters();
        SecureRandomParameters srp = null;
        KeyManagersType kmt = null;
        TrustManagersType tmt = null;
        boolean enableRevocation = false;
        boolean disableTrustVerification = false;
        while (keys2.hasMoreElements()) {
            StringTokenizer st;
            String k = keys2.nextElement();
            if (!k.startsWith("tlsClientParameters.")) continue;
            if (p == null) {
                p = new TLSClientParameters();
                c.setTlsClientParameters(p);
            }
            String v = d.get(k);
            if ("secureSocketProtocol".equals(k = k.substring("tlsClientParameters.".length()))) {
                p.setSecureSocketProtocol(v);
                continue;
            }
            if ("sslCacheTimeout".equals(k)) {
                p.setSslCacheTimeout(Integer.parseInt(v));
                continue;
            }
            if ("jsseProvider".equals(k)) {
                p.setJsseProvider(v);
                continue;
            }
            if ("disableCNCheck".equals(k)) {
                p.setDisableCNCheck(Boolean.parseBoolean(v));
                continue;
            }
            if ("useHttpsURLConnectionDefaultHostnameVerifier".equals(k)) {
                p.setUseHttpsURLConnectionDefaultHostnameVerifier(Boolean.parseBoolean(v));
                continue;
            }
            if ("useHttpsURLConnectionDefaultSslSocketFactory".equals(k)) {
                p.setUseHttpsURLConnectionDefaultSslSocketFactory(Boolean.parseBoolean(v));
                continue;
            }
            if ("enableRevocation".equals(k)) {
                enableRevocation = Boolean.parseBoolean(v);
                continue;
            }
            if (k.startsWith("certConstraints.")) {
                this.parseCertConstaints(p, k, v);
                continue;
            }
            if (k.startsWith("secureRandomParameters.")) {
                k = k.substring("secureRandomParameters.".length());
                if (srp == null) {
                    srp = new SecureRandomParameters();
                }
                if ("algorithm".equals(k)) {
                    srp.setAlgorithm(v);
                    continue;
                }
                if (!"provider".equals(k)) continue;
                srp.setProvider(v);
                continue;
            }
            if (k.startsWith("cipherSuitesFilter.")) {
                List<String> lst;
                k = k.substring("cipherSuitesFilter.".length());
                st = new StringTokenizer(v, ",");
                FiltersType ft = p.getCipherSuitesFilter();
                if (ft == null) {
                    ft = new FiltersType();
                    p.setCipherSuitesFilter(ft);
                }
                List<String> list = lst = "include".equals(k) ? ft.getInclude() : ft.getExclude();
                while (st.hasMoreTokens()) {
                    lst.add(st.nextToken());
                }
                continue;
            }
            if (k.startsWith("cipherSuites")) {
                st = new StringTokenizer(v, ",");
                while (st.hasMoreTokens()) {
                    p.getCipherSuites().add(st.nextToken());
                }
                continue;
            }
            if ("trustManagers.disableTrustVerification".equals(k) && Boolean.parseBoolean(v)) {
                disableTrustVerification = true;
                continue;
            }
            if (k.startsWith("trustManagers.")) {
                tmt = this.getTrustManagers(tmt, k.substring("trustManagers.".length()), v);
                continue;
            }
            if (!k.startsWith("keyManagers.")) continue;
            kmt = this.getKeyManagers(kmt, k.substring("keyManagers.".length()), v);
        }
        try {
            if (srp != null) {
                p.setSecureRandom(TLSParameterJaxBUtils.getSecureRandom(srp));
            }
            if (kmt != null) {
                p.setKeyManagers(TLSParameterJaxBUtils.getKeyManagers(kmt));
            }
            if (disableTrustVerification) {
                p.setTrustManagers(InsecureTrustManager.getNoOpX509TrustManagers());
            } else if (tmt != null) {
                p.setTrustManagers(TLSParameterJaxBUtils.getTrustManagers(tmt, enableRevocation));
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void parseCertConstaints(TLSClientParameters p, String k, String v) {
        k = k.substring("certConstraints.".length());
        CertificateConstraintsType cct = p.getCertConstraints();
        if (cct == null) {
            cct = new CertificateConstraintsType();
            p.setCertConstraints(cct);
        }
        DNConstraintsType dnct = null;
        if (k.startsWith("SubjectDNConstraints.")) {
            dnct = cct.getSubjectDNConstraints();
            if (dnct == null) {
                dnct = new DNConstraintsType();
                cct.setSubjectDNConstraints(dnct);
            }
            k = k.substring("SubjectDNConstraints.".length());
        } else if (k.startsWith("IssuerDNConstraints.")) {
            dnct = cct.getIssuerDNConstraints();
            if (dnct == null) {
                dnct = new DNConstraintsType();
                cct.setIssuerDNConstraints(dnct);
            }
            k = k.substring("IssuerDNConstraints.".length());
        }
        if (dnct != null) {
            if ("combinator".equals(k)) {
                dnct.setCombinator(CombinatorType.fromValue(v));
            } else if ("RegularExpression".equals(k)) {
                dnct.getRegularExpression().add(k);
            }
        }
    }

    private KeyManagersType getKeyManagers(KeyManagersType keyManagers, String k, String v) {
        if (keyManagers == null) {
            keyManagers = new KeyManagersType();
        }
        if ("factoryAlgorithm".equals(k)) {
            keyManagers.setFactoryAlgorithm(v);
        } else if ("provider".equals(k)) {
            keyManagers.setProvider(v);
        } else if ("keyPassword".equals(k)) {
            keyManagers.setKeyPassword(v);
        } else if (k.startsWith("keyStore.")) {
            keyManagers.setKeyStore(this.getKeyStore(keyManagers.getKeyStore(), k.substring("keyStore.".length()), v));
        }
        return keyManagers;
    }

    private KeyStoreType getKeyStore(KeyStoreType ks, String k, String v) {
        if (ks == null) {
            ks = new KeyStoreType();
        }
        if ("type".equals(k)) {
            ks.setType(v);
        } else if ("password".equals(k)) {
            ks.setPassword(v);
        } else if ("provider".equals(k)) {
            ks.setProvider(v);
        } else if ("url".equals(k)) {
            ks.setUrl(v);
        } else if ("file".equals(k)) {
            ks.setFile(v);
        } else if ("resource".equals(k)) {
            ks.setResource(v);
        }
        return ks;
    }

    private TrustManagersType getTrustManagers(TrustManagersType tmt, String k, String v) {
        if (tmt == null) {
            tmt = new TrustManagersType();
        }
        if ("provider".equals(k)) {
            tmt.setProvider(v);
        } else if ("factoryAlgorithm".equals(k)) {
            tmt.setFactoryAlgorithm(v);
        } else if (k.startsWith("keyStore.")) {
            tmt.setKeyStore(this.getKeyStore(tmt.getKeyStore(), k.substring("keyStore.".length()), v));
        } else if (k.startsWith("certStore")) {
            tmt.setCertStore(this.getCertStore(tmt.getCertStore(), k.substring("certStore.".length()), v));
        }
        return tmt;
    }

    private CertStoreType getCertStore(CertStoreType cs, String k, String v) {
        if (cs == null) {
            cs = new CertStoreType();
        }
        if ("file".equals(k)) {
            cs.setFile(v);
        } else if ("url".equals(k)) {
            cs.setUrl(v);
        } else if ("resource".equals(k)) {
            cs.setResource(v);
        }
        return cs;
    }

    private void applyProxyAuthorization(Dictionary<String, String> d, HTTPConduit c) {
        Enumeration<String> keys2 = d.keys();
        ProxyAuthorizationPolicy p = c.getProxyAuthorization();
        while (keys2.hasMoreElements()) {
            String k = keys2.nextElement();
            if (!k.startsWith("proxyAuthorization.")) continue;
            if (p == null) {
                p = new ProxyAuthorizationPolicy();
                c.setProxyAuthorization(p);
            }
            String v = d.get(k);
            if ("UserName".equals(k = k.substring("proxyAuthorization.".length()))) {
                p.setUserName(v);
                continue;
            }
            if ("Password".equals(k)) {
                p.setPassword(v);
                continue;
            }
            if ("Authorization".equals(k)) {
                p.setAuthorization(v);
                continue;
            }
            if (!"AuthorizationType".equals(k)) continue;
            p.setAuthorizationType(v);
        }
    }

    private void applyAuthorization(Dictionary<String, String> d, HTTPConduit c) {
        Enumeration<String> keys2 = d.keys();
        AuthorizationPolicy p = c.getAuthorization();
        while (keys2.hasMoreElements()) {
            String k = keys2.nextElement();
            if (!k.startsWith("authorization.")) continue;
            if (p == null) {
                p = new AuthorizationPolicy();
                c.setAuthorization(p);
            }
            String v = d.get(k);
            if ("UserName".equals(k = k.substring("authorization.".length()))) {
                p.setUserName(v);
                continue;
            }
            if ("Password".equals(k)) {
                p.setPassword(v);
                continue;
            }
            if ("Authorization".equals(k)) {
                p.setAuthorization(v);
                continue;
            }
            if (!"AuthorizationType".equals(k)) continue;
            p.setAuthorizationType(v);
        }
    }

    private void applyClientPolicies(Dictionary<String, String> d, HTTPConduit c) {
        Enumeration<String> keys2 = d.keys();
        HTTPClientPolicy p = c.getClient();
        while (keys2.hasMoreElements()) {
            String k = keys2.nextElement();
            if (!k.startsWith("client.")) continue;
            if (p == null) {
                p = new HTTPClientPolicy();
                c.setClient(p);
            }
            String v = d.get(k);
            if ("ConnectionTimeout".equals(k = k.substring("client.".length()))) {
                p.setConnectionTimeout(Long.parseLong(v.trim()));
                continue;
            }
            if ("ReceiveTimeout".equals(k)) {
                p.setReceiveTimeout(Long.parseLong(v.trim()));
                continue;
            }
            if ("AsyncExecuteTimeout".equals(k)) {
                p.setAsyncExecuteTimeout(Long.parseLong(v.trim()));
                continue;
            }
            if ("AsyncExecuteTimeoutRejection".equals(k)) {
                p.setAsyncExecuteTimeoutRejection(Boolean.parseBoolean(v.trim()));
                continue;
            }
            if ("AutoRedirect".equals(k)) {
                p.setAutoRedirect(Boolean.parseBoolean(v.trim()));
                continue;
            }
            if ("MaxRetransmits".equals(k)) {
                p.setMaxRetransmits(Integer.parseInt(v.trim()));
                continue;
            }
            if ("AllowChunking".equals(k)) {
                p.setAllowChunking(Boolean.parseBoolean(v.trim()));
                continue;
            }
            if ("ChunkingThreshold".equals(k)) {
                p.setChunkingThreshold(Integer.parseInt(v.trim()));
                continue;
            }
            if ("ChunkLength".equals(k)) {
                p.setChunkLength(Integer.parseInt(v.trim()));
                continue;
            }
            if ("Connection".equals(k)) {
                p.setConnection(ConnectionType.valueOf(v));
                continue;
            }
            if ("DecoupledEndpoint".equals(k)) {
                p.setDecoupledEndpoint(v);
                continue;
            }
            if ("ProxyServer".equals(k)) {
                p.setProxyServer(v);
                continue;
            }
            if ("ProxyServerPort".equals(k)) {
                p.setProxyServerPort(Integer.parseInt(v.trim()));
                continue;
            }
            if ("ProxyServerType".equals(k)) {
                p.setProxyServerType(ProxyServerType.fromValue(v));
                continue;
            }
            if (!"NonProxyHosts".equals(k)) continue;
            p.setNonProxyHosts(v);
        }
    }

    private void applyAuthSupplier(Dictionary<String, String> d, HTTPConduit c) {
        Enumeration<String> keys2 = d.keys();
        while (keys2.hasMoreElements()) {
            Object obj;
            String k = keys2.nextElement();
            if (!k.startsWith("authSupplier")) continue;
            String v = d.get(k);
            try {
                obj = Class.forName(v).newInstance();
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
            if (!(obj instanceof HttpAuthSupplier)) continue;
            c.setAuthSupplier((HttpAuthSupplier)obj);
        }
    }
}

