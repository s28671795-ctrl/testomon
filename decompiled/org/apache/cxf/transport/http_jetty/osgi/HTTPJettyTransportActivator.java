/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.ServiceRegistration
 *  org.osgi.service.cm.ConfigurationException
 *  org.osgi.service.cm.ManagedServiceFactory
 *  org.osgi.util.tracker.ServiceTracker
 */
package org.apache.cxf.transport.http_jetty.osgi;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import javax.management.MBeanServer;
import org.apache.cxf.bus.blueprint.BlueprintNameSpaceHandlerFactory;
import org.apache.cxf.bus.blueprint.NamespaceHandlerRegisterer;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.configuration.jsse.TLSParameterBase;
import org.apache.cxf.configuration.jsse.TLSParameterJaxBUtils;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.security.CertStoreType;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.configuration.security.CombinatorType;
import org.apache.cxf.configuration.security.DNConstraintsType;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.KeyStoreType;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TrustManagersType;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.apache.cxf.transport.http_jetty.ThreadingParameters;
import org.apache.cxf.transport.http_jetty.blueprint.HTTPJettyTransportNamespaceHandler;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.util.tracker.ServiceTracker;

public class HTTPJettyTransportActivator
implements BundleActivator,
ManagedServiceFactory {
    public static final String FACTORY_PID = "org.apache.cxf.http.jetty";
    BundleContext context;
    MBeanServer mbeans;
    ServiceTracker<MBeanServer, ?> mbeanServerTracker;
    ServiceRegistration<ManagedServiceFactory> reg;
    JettyHTTPServerEngineFactory factory = new JettyHTTPServerEngineFactory(){

        @Override
        public MBeanServer getMBeanServer() {
            return (MBeanServer)HTTPJettyTransportActivator.this.mbeanServerTracker.getService();
        }
    };

    public void start(BundleContext ctx) throws Exception {
        this.context = ctx;
        this.reg = this.context.registerService(ManagedServiceFactory.class, (Object)this, CollectionUtils.singletonDictionary("service.pid", FACTORY_PID));
        this.mbeanServerTracker = new ServiceTracker(ctx, MBeanServer.class, null);
        try {
            BlueprintNameSpaceHandlerFactory nsHandlerFactory = new BlueprintNameSpaceHandlerFactory(){

                @Override
                public Object createNamespaceHandler() {
                    return new HTTPJettyTransportNamespaceHandler();
                }
            };
            NamespaceHandlerRegisterer.register(this.context, nsHandlerFactory, "http://cxf.apache.org/transports/http-jetty/configuration");
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            // empty catch block
        }
    }

    public void stop(BundleContext ctx) throws Exception {
        this.mbeanServerTracker.close();
        this.reg.unregister();
    }

    public String getName() {
        return FACTORY_PID;
    }

    public void updated(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
        if (pid == null) {
            return;
        }
        int port = Integer.parseInt((String)properties.get("port"));
        String host2 = (String)properties.get("host");
        try {
            TLSServerParameters tls = this.createTlsServerParameters(properties);
            if (tls != null) {
                this.factory.setTLSServerParametersForPort(host2, port, tls);
            } else {
                this.factory.createJettyHTTPServerEngine(host2, port, "http");
            }
            JettyHTTPServerEngine e = this.factory.retrieveJettyHTTPServerEngine(port);
            this.configure(e, properties);
        }
        catch (IOException | GeneralSecurityException e) {
            throw new ConfigurationException(null, null, (Throwable)e);
        }
    }

    private void configure(JettyHTTPServerEngine e, Dictionary<String, ?> properties) {
        ThreadingParameters threading = this.createThreadingParameters(properties);
        if (threading != null) {
            e.setThreadingParameters(threading);
        }
        Enumeration<String> keys2 = properties.keys();
        while (keys2.hasMoreElements()) {
            String k = keys2.nextElement();
            if ("sessionSupport".equals(k)) {
                e.setSessionSupport(Boolean.parseBoolean((String)properties.get(k)));
                continue;
            }
            if ("continuationsEnabled".equals(k)) {
                e.setContinuationsEnabled(Boolean.parseBoolean((String)properties.get(k)));
                continue;
            }
            if ("sendServerVersion".equals(k)) {
                e.setSendServerVersion(Boolean.parseBoolean((String)properties.get(k)));
                continue;
            }
            if ("reuseAddress".equals(k)) {
                e.setReuseAddress(Boolean.parseBoolean((String)properties.get(k)));
                continue;
            }
            if ("maxIdleTime".equals(k)) {
                e.setMaxIdleTime(Integer.parseInt((String)properties.get(k)));
                continue;
            }
            if (!"sessionTimeout".equals(k)) continue;
            e.setSessionTimeout(Integer.parseInt((String)properties.get(k)));
        }
    }

    public void deleted(String pid) {
    }

    private ThreadingParameters createThreadingParameters(Dictionary<String, ?> d) {
        Enumeration<String> keys2 = d.keys();
        ThreadingParameters p = null;
        while (keys2.hasMoreElements()) {
            String k = keys2.nextElement();
            if (!k.startsWith("threadingParameters.")) continue;
            if (p == null) {
                p = new ThreadingParameters();
            }
            String v = (String)d.get(k);
            if ("minThreads".equals(k = k.substring("threadingParameters.".length()))) {
                p.setMinThreads(Integer.parseInt(v));
                continue;
            }
            if ("maxThreads".equals(k)) {
                p.setMaxThreads(Integer.parseInt(v));
                continue;
            }
            if (!"threadNamePrefix".equals(k)) continue;
            p.setThreadNamePrefix(k);
        }
        return p;
    }

    private TLSServerParameters createTlsServerParameters(Dictionary<String, ?> d) {
        Enumeration<String> keys2 = d.keys();
        TLSParameterBase p = null;
        SecureRandomParameters srp = null;
        KeyManagersType kmt = null;
        TrustManagersType tmt = null;
        boolean enableRevocation = false;
        while (keys2.hasMoreElements()) {
            StringTokenizer st;
            String k = keys2.nextElement();
            if (!k.startsWith("tlsServerParameters.")) continue;
            if (p == null) {
                p = new TLSServerParameters();
            }
            String v = (String)d.get(k);
            if ("secureSocketProtocol".equals(k = k.substring("tlsServerParameters.".length()))) {
                p.setSecureSocketProtocol(v);
                continue;
            }
            if ("jsseProvider".equals(k)) {
                p.setJsseProvider(v);
                continue;
            }
            if ("certAlias".equals(k)) {
                p.setCertAlias(v);
                continue;
            }
            if ("clientAuthentication.want".equals(k)) {
                if (((TLSServerParameters)p).getClientAuthentication() == null) {
                    ((TLSServerParameters)p).setClientAuthentication(new ClientAuthentication());
                }
                ((TLSServerParameters)p).getClientAuthentication().setWant(Boolean.parseBoolean(v));
                continue;
            }
            if ("clientAuthentication.required".equals(k)) {
                if (((TLSServerParameters)p).getClientAuthentication() == null) {
                    ((TLSServerParameters)p).setClientAuthentication(new ClientAuthentication());
                }
                ((TLSServerParameters)p).getClientAuthentication().setRequired(Boolean.parseBoolean(v));
                continue;
            }
            if ("enableRevocation".equals(k)) {
                enableRevocation = Boolean.parseBoolean(v);
                continue;
            }
            if (k.startsWith("certConstraints.")) {
                this.configureCertConstraints((TLSServerParameters)p, k, v);
                continue;
            }
            if (k.startsWith("secureRandomParameters.")) {
                srp = this.configureSecureRandom(srp, k, v);
                continue;
            }
            if (k.startsWith("cipherSuitesFilter.")) {
                this.configureCipherSuitesFilter((TLSServerParameters)p, k, v);
                continue;
            }
            if (k.startsWith("cipherSuites")) {
                st = new StringTokenizer(v, ",");
                while (st.hasMoreTokens()) {
                    p.getCipherSuites().add(st.nextToken());
                }
                continue;
            }
            if (k.startsWith("excludeProtocols")) {
                st = new StringTokenizer(v, ",");
                while (st.hasMoreTokens()) {
                    ((TLSServerParameters)p).getExcludeProtocols().add(st.nextToken());
                }
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
            if (tmt != null) {
                p.setTrustManagers(TLSParameterJaxBUtils.getTrustManagers(tmt, enableRevocation));
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    private void configureCipherSuitesFilter(TLSServerParameters p, String k, String v) {
        List<String> lst;
        k = k.substring("cipherSuitesFilter.".length());
        StringTokenizer st = new StringTokenizer(v, ",");
        FiltersType ft = p.getCipherSuitesFilter();
        if (ft == null) {
            ft = new FiltersType();
            p.setCipherSuitesFilter(ft);
        }
        List<String> list = lst = "include".equals(k) ? ft.getInclude() : ft.getExclude();
        while (st.hasMoreTokens()) {
            lst.add(st.nextToken());
        }
    }

    private SecureRandomParameters configureSecureRandom(SecureRandomParameters srp, String k, String v) {
        k = k.substring("secureRandomParameters.".length());
        if (srp == null) {
            srp = new SecureRandomParameters();
        }
        if ("algorithm".equals(k)) {
            srp.setAlgorithm(v);
        } else if ("provider".equals(k)) {
            srp.setProvider(v);
        }
        return srp;
    }

    private void configureCertConstraints(TLSServerParameters p, String k, String v) {
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
}

