/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jetty;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.management.MBeanServer;
import org.apache.cxf.Bus;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.management.InstrumentationManager;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;
import org.apache.cxf.transport.http_jetty.ThreadingParameters;
import org.eclipse.jetty.util.component.Container;

@NoJSR250Annotations(unlessNull={"bus"})
public class JettyHTTPServerEngineFactory {
    private static final Logger LOG = LogUtils.getL7dLogger(JettyHTTPServerEngineFactory.class);
    private static final int FALLBACK_THREADING_PARAMS_KEY = 0;
    private static ConcurrentHashMap<Integer, JettyHTTPServerEngine> portMap = new ConcurrentHashMap();
    private BusLifeCycleManager lifeCycleManager;
    private Map<String, ThreadingParameters> threadingParametersMap = new TreeMap<String, ThreadingParameters>();
    private ThreadingParameters fallbackThreadingParameters;
    private Map<String, TLSServerParameters> tlsParametersMap = new TreeMap<String, TLSServerParameters>();
    private Bus bus;
    private Container.Listener mBeanContainer;

    public JettyHTTPServerEngineFactory() {
    }

    public JettyHTTPServerEngineFactory(Bus b) {
        this.setBus(b);
    }

    public JettyHTTPServerEngineFactory(Bus b, Map<String, TLSServerParameters> tls, Map<String, ThreadingParameters> threading) {
        this.tlsParametersMap.putAll(tls);
        this.threadingParametersMap.putAll(threading);
        this.setBus(b);
    }

    private static JettyHTTPServerEngine getOrCreate(JettyHTTPServerEngineFactory factory2, String host2, int port, TLSServerParameters tlsParams) throws IOException, GeneralSecurityException {
        JettyHTTPServerEngine ref = portMap.get(port);
        if (ref == null) {
            ref = new JettyHTTPServerEngine(factory2.getMBeanContainer(), host2, port);
            if (tlsParams != null) {
                ref.setTlsServerParameters(tlsParams);
            }
            JettyHTTPServerEngine tmpRef = portMap.putIfAbsent(port, ref);
            ref.finalizeConfig();
            if (tmpRef != null) {
                ref = tmpRef;
            }
        }
        return ref;
    }

    @Resource(name="cxf")
    public final void setBus(Bus bus) {
        this.bus = bus;
        if (bus != null) {
            bus.setExtension(this, JettyHTTPServerEngineFactory.class);
            this.lifeCycleManager = bus.getExtension(BusLifeCycleManager.class);
            if (null != this.lifeCycleManager) {
                this.lifeCycleManager.registerLifeCycleListener(new JettyBusLifeCycleListener());
            }
        }
    }

    public Bus getBus() {
        return this.bus;
    }

    public void setTlsServerParametersMap(Map<String, TLSServerParameters> tlsParamsMap) {
        this.tlsParametersMap = tlsParamsMap;
    }

    public Map<String, TLSServerParameters> getTlsServerParametersMap() {
        return this.tlsParametersMap;
    }

    public void setEnginesList(List<JettyHTTPServerEngine> enginesList) {
        for (JettyHTTPServerEngine engine : enginesList) {
            if (engine.getPort() == 0) {
                this.fallbackThreadingParameters = engine.getThreadingParameters();
            }
            portMap.putIfAbsent(engine.getPort(), engine);
        }
    }

    public void setThreadingParametersMap(Map<String, ThreadingParameters> threadingParamsMap) {
        this.threadingParametersMap = threadingParamsMap;
    }

    public Map<String, ThreadingParameters> getThreadingParametersMap() {
        return this.threadingParametersMap;
    }

    public void setTLSServerParametersForPort(String host2, int port, TLSServerParameters tlsParams) throws GeneralSecurityException, IOException {
        if (tlsParams == null) {
            throw new IllegalArgumentException("tlsParams cannot be null");
        }
        JettyHTTPServerEngine ref = this.retrieveJettyHTTPServerEngine(port);
        if (null == ref) {
            JettyHTTPServerEngineFactory.getOrCreate(this, host2, port, tlsParams);
        } else {
            if (ref.getConnector() != null && ref.getConnector().isRunning()) {
                throw new IOException("can't set the TLS params on the opened connector");
            }
            ref.setTlsServerParameters(tlsParams);
        }
    }

    public void setTLSServerParametersForPort(int port, TLSServerParameters tlsParams) throws GeneralSecurityException, IOException {
        this.setTLSServerParametersForPort(null, port, tlsParams);
    }

    public synchronized JettyHTTPServerEngine retrieveJettyHTTPServerEngine(int port) {
        return portMap.get(port);
    }

    public synchronized JettyHTTPServerEngine createJettyHTTPServerEngine(String host2, int port, String protocol, String id) throws GeneralSecurityException, IOException {
        JettyHTTPServerEngine ref;
        LOG.fine("Creating Jetty HTTP Server Engine for port " + port + ".");
        TLSServerParameters tlsParameters = null;
        if (id != null && this.tlsParametersMap != null && this.tlsParametersMap.containsKey(id)) {
            tlsParameters = this.tlsParametersMap.get(id);
        }
        if (!protocol.equals((ref = JettyHTTPServerEngineFactory.getOrCreate(this, host2, port, tlsParameters)).getProtocol())) {
            throw new IOException("Protocol mismatch for port " + port + ": engine's protocol is " + ref.getProtocol() + ", the url protocol is " + protocol);
        }
        if (!ref.isSetThreadingParameters() && null != this.fallbackThreadingParameters) {
            if (LOG.isLoggable(Level.INFO)) {
                int min2 = this.fallbackThreadingParameters.getMinThreads();
                int max = this.fallbackThreadingParameters.getMaxThreads();
                String threadNamePrefix = this.fallbackThreadingParameters.getThreadNamePrefix();
                LOG.log(Level.INFO, "FALLBACK_THREADING_PARAMETERS_MSG", new Object[]{port, min2, max, threadNamePrefix});
            }
            ref.setThreadingParameters(this.fallbackThreadingParameters);
        }
        return ref;
    }

    public synchronized JettyHTTPServerEngine createJettyHTTPServerEngine(int port, String protocol) throws GeneralSecurityException, IOException {
        return this.createJettyHTTPServerEngine(null, port, protocol);
    }

    public synchronized JettyHTTPServerEngine createJettyHTTPServerEngine(String host2, int port, String protocol) throws GeneralSecurityException, IOException {
        return this.createJettyHTTPServerEngine(host2, port, protocol, null);
    }

    public static synchronized void destroyForPort(int port) {
        JettyHTTPServerEngine ref = portMap.remove(port);
        if (ref != null) {
            LOG.fine("Stopping Jetty HTTP Server Engine on port " + port + ".");
            try {
                ref.stop();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MBeanServer getMBeanServer() {
        if (this.bus != null && this.bus.getExtension(InstrumentationManager.class) != null) {
            return this.bus.getExtension(InstrumentationManager.class).getMBeanServer();
        }
        return null;
    }

    public synchronized Container.Listener getMBeanContainer() {
        if (this.mBeanContainer != null) {
            return this.mBeanContainer;
        }
        MBeanServer mbs = this.getMBeanServer();
        if (mbs != null) {
            try {
                Class<?> cls = ClassLoaderUtils.loadClass("org.eclipse.jetty.jmx.MBeanContainer", this.getClass());
                this.mBeanContainer = (Container.Listener)cls.getConstructor(MBeanServer.class).newInstance(mbs);
                try {
                    cls.getMethod("start", null).invoke((Object)this.mBeanContainer, (Object[])null);
                }
                catch (NoSuchMethodException noSuchMethodException) {}
            }
            catch (Throwable ex) {
                LOG.info("Could not load or start org.eclipse.management.MBeanContainer.  Jetty JMX support will not be enabled: " + ex.getMessage());
            }
        }
        return this.mBeanContainer;
    }

    public void initComplete() {
    }

    public void postShutdown() {
        JettyHTTPServerEngine[] engines;
        for (JettyHTTPServerEngine engine : engines = portMap.values().toArray(new JettyHTTPServerEngine[portMap.values().size()])) {
            engine.shutdown();
        }
        this.threadingParametersMap.clear();
        this.tlsParametersMap.clear();
        this.mBeanContainer = null;
    }

    public void preShutdown() {
    }

    private class JettyBusLifeCycleListener
    implements BusLifeCycleListener {
        private JettyBusLifeCycleListener() {
        }

        @Override
        public void initComplete() {
            JettyHTTPServerEngineFactory.this.initComplete();
        }

        @Override
        public void preShutdown() {
            JettyHTTPServerEngineFactory.this.preShutdown();
        }

        @Override
        public void postShutdown() {
            JettyHTTPServerEngineFactory.this.postShutdown();
        }
    }
}

