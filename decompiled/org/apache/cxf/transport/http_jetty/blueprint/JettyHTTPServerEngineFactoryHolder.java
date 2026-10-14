/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http_jetty.blueprint;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.jsse.TLSServerParametersConfig;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.apache.cxf.transport.http_jetty.ThreadingParameters;
import org.apache.cxf.transports.http_jetty.configuration.JettyHTTPServerEngineConfigType;
import org.apache.cxf.transports.http_jetty.configuration.JettyHTTPServerEngineFactoryConfigType;
import org.apache.cxf.transports.http_jetty.configuration.TLSServerParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersType;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.w3c.dom.Element;

public class JettyHTTPServerEngineFactoryHolder {
    private static final Logger LOG = LogUtils.getL7dLogger(JettyHTTPServerEngineFactoryHolder.class);
    private String parsedElement;
    private JettyHTTPServerEngineFactory factory;
    private Map<String, Connector> connectorMap;
    private Map<String, List<Handler>> handlersMap;
    private JAXBContext jaxbContext;
    private Set<Class<?>> jaxbClasses;

    public void init() {
        try {
            Element element = StaxUtils.read(new StringReader(this.parsedElement)).getDocumentElement();
            JettyHTTPServerEngineFactoryConfigType config = this.getJaxbObject(element, JettyHTTPServerEngineFactoryConfigType.class);
            Bus defaultBus = BusFactory.getDefaultBus();
            this.factory = new JettyHTTPServerEngineFactory(defaultBus);
            TreeMap<String, ThreadingParameters> threadingParametersMap = new TreeMap<String, ThreadingParameters>();
            if (config.getIdentifiedThreadingParameters() != null) {
                for (ThreadingParametersIdentifiedType threadingParametersIdentifiedType : config.getIdentifiedThreadingParameters()) {
                    ThreadingParameters rThreads = new ThreadingParameters();
                    String id = threadingParametersIdentifiedType.getId();
                    if (threadingParametersIdentifiedType.getThreadingParameters().getMaxThreads() != null) {
                        rThreads.setMaxThreads(threadingParametersIdentifiedType.getThreadingParameters().getMaxThreads());
                    }
                    if (threadingParametersIdentifiedType.getThreadingParameters().getMinThreads() != null) {
                        rThreads.setMinThreads(threadingParametersIdentifiedType.getThreadingParameters().getMinThreads());
                    }
                    rThreads.setThreadNamePrefix(threadingParametersIdentifiedType.getThreadingParameters().getThreadNamePrefix());
                    threadingParametersMap.put(id, rThreads);
                }
                this.factory.setThreadingParametersMap(threadingParametersMap);
            }
            TreeMap<String, TLSServerParameters> sslMap = new TreeMap<String, TLSServerParameters>();
            if (config.getIdentifiedTLSServerParameters() != null) {
                for (TLSServerParametersIdentifiedType t2 : config.getIdentifiedTLSServerParameters()) {
                    try {
                        TLSServerParametersConfig parameter = new TLSServerParametersConfig(t2.getTlsServerParameters());
                        sslMap.put(t2.getId(), parameter);
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Could not configure TLS for id " + t2.getId(), e);
                    }
                }
                this.factory.setTlsServerParametersMap(sslMap);
            }
            ArrayList<JettyHTTPServerEngine> arrayList = new ArrayList<JettyHTTPServerEngine>();
            for (JettyHTTPServerEngineConfigType engine : config.getEngine()) {
                JettyHTTPServerEngine eng = new JettyHTTPServerEngine(this.factory.getMBeanContainer(), engine.getHost(), engine.getPort());
                if (engine.getConnector() != null && this.connectorMap != null) {
                    Connector connector = this.connectorMap.get(engine.getPort().toString());
                    if (connector != null) {
                        eng.setConnector(connector);
                    } else {
                        throw new RuntimeException("Could not find the connector instance for engine with port" + engine.getPort().toString());
                    }
                }
                if (engine.getHandlers() != null && this.handlersMap != null) {
                    List<Handler> handlers = this.handlersMap.get(engine.getPort().toString());
                    if (handlers != null) {
                        eng.setHandlers(handlers);
                    } else {
                        throw new RuntimeException("Could not find the handlers instance for engine with port" + engine.getPort().toString());
                    }
                }
                if (engine.isContinuationsEnabled() != null) {
                    eng.setContinuationsEnabled(engine.isContinuationsEnabled());
                }
                if (engine.isSendServerVersion() != null) {
                    eng.setSendServerVersion(engine.isSendServerVersion());
                }
                if (engine.getHost() != null && !StringUtils.isEmpty(engine.getHost())) {
                    eng.setHost(engine.getHost());
                }
                if (engine.getMaxIdleTime() != null) {
                    eng.setMaxIdleTime(engine.getMaxIdleTime());
                }
                if (engine.getPort() != null) {
                    eng.setPort(engine.getPort());
                }
                if (engine.isReuseAddress() != null) {
                    eng.setReuseAddress(engine.isReuseAddress());
                }
                if (engine.isSessionSupport() != null) {
                    eng.setSessionSupport(engine.isSessionSupport());
                }
                if (engine.getSessionTimeout() != null) {
                    eng.setSessionTimeout(engine.getSessionTimeout());
                }
                if (engine.getThreadingParameters() != null) {
                    ThreadingParametersType threads = engine.getThreadingParameters();
                    ThreadingParameters rThreads = new ThreadingParameters();
                    if (threads.getMaxThreads() != null) {
                        rThreads.setMaxThreads(threads.getMaxThreads());
                    }
                    if (threads.getMinThreads() != null) {
                        rThreads.setMinThreads(threads.getMinThreads());
                    }
                    eng.setThreadingParameters(rThreads);
                }
                if (engine.getTlsServerParameters() != null && (engine.getTlsServerParameters().getKeyManagers() != null || engine.getTlsServerParameters().getTrustManagers() != null)) {
                    TLSServerParametersConfig parameter = null;
                    try {
                        parameter = new TLSServerParametersConfig(engine.getTlsServerParameters());
                        eng.setTlsServerParameters(parameter);
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Could not configure TLS for engine on  " + eng.getHost() + ":" + eng.getPort(), e);
                    }
                }
                eng.finalizeConfig();
                arrayList.add(eng);
            }
            this.factory.setEnginesList(arrayList);
            this.factory.initComplete();
        }
        catch (Exception e) {
            throw new RuntimeException("Could not process configuration.", e);
        }
    }

    public void destroy() {
        this.factory.postShutdown();
        this.jaxbClasses.clear();
        this.jaxbContext = null;
    }

    public String getParsedElement() {
        return this.parsedElement;
    }

    public void setParsedElement(String parsedElement) {
        this.parsedElement = parsedElement;
    }

    public void setConnectorMap(Map<String, Connector> connectorMap) {
        this.connectorMap = connectorMap;
    }

    public void setHandlersMap(Map<String, List<Handler>> handlersMap) {
        this.handlersMap = handlersMap;
    }

    protected <T> T getJaxbObject(Element parent, Class<T> c) {
        try {
            JAXBElement<T> ele = JAXBUtils.unmarshall(this.getContext(c), parent, c);
            return ele.getValue();
        }
        catch (JAXBException e) {
            LOG.warning("Unable to parse property due to " + e);
            return null;
        }
    }

    protected synchronized JAXBContext getContext(Class<?> cls) {
        if (this.jaxbContext == null || this.jaxbClasses == null || !this.jaxbClasses.contains(cls)) {
            try {
                HashSet tmp = new HashSet();
                if (this.jaxbClasses != null) {
                    tmp.addAll(this.jaxbClasses);
                }
                JAXBContextCache.addPackage(tmp, PackageUtils.getPackageName(cls), cls == null ? this.getClass().getClassLoader() : cls.getClassLoader());
                if (cls != null) {
                    boolean hasOf = false;
                    for (Class clazz : tmp) {
                        if (clazz.getPackage() != cls.getPackage() || !"ObjectFactory".equals(clazz.getSimpleName())) continue;
                        hasOf = true;
                    }
                    if (!hasOf) {
                        tmp.add(cls);
                    }
                }
                JAXBContextCache.scanPackages(tmp);
                JAXBContextCache.CachedContextAndSchemas ccs = JAXBContextCache.getCachedContextAndSchemas(tmp, null, null, null, false);
                this.jaxbClasses = ccs.getClasses();
                this.jaxbContext = ccs.getContext();
            }
            catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return this.jaxbContext;
    }
}

