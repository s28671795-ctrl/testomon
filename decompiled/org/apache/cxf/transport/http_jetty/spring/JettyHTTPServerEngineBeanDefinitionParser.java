/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConstructorArgumentValues$ValueHolder
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.transport.http_jetty.spring;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.jsse.TLSServerParametersConfig;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.CipherSuites;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.configuration.security.ExcludeProtocols;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.IncludeProtocols;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TLSServerParametersType;
import org.apache.cxf.configuration.security.TrustManagersType;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.apache.cxf.transport.http_jetty.ThreadingParameters;
import org.apache.cxf.transports.http_jetty.configuration.TLSServerParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class JettyHTTPServerEngineBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    private static final String SECURITY_NS = "http://cxf.apache.org/configuration/security";

    @Override
    public void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        String sendServerVersionStr;
        String maxIdleTimeStr;
        String continuationsStr;
        String portStr = element.getAttribute("port");
        bean.addPropertyValue("port", (Object)portStr);
        String hostStr = element.getAttribute("host");
        if (hostStr != null && !"".equals(hostStr.trim())) {
            bean.addPropertyValue("host", (Object)hostStr);
        }
        if ((continuationsStr = element.getAttribute("continuationsEnabled")) != null && continuationsStr.length() > 0) {
            bean.addPropertyValue("continuationsEnabled", (Object)continuationsStr);
        }
        if ((maxIdleTimeStr = element.getAttribute("maxIdleTime")) != null && !"".equals(maxIdleTimeStr.trim())) {
            bean.addPropertyValue("maxIdleTime", (Object)maxIdleTimeStr);
        }
        if ((sendServerVersionStr = element.getAttribute("sendServerVersion")) != null && sendServerVersionStr.length() > 0) {
            bean.addPropertyValue("sendServerVersion", (Object)sendServerVersionStr);
        }
        ConstructorArgumentValues.ValueHolder busValue = ctx.getContainingBeanDefinition().getConstructorArgumentValues().getArgumentValue(0, Bus.class);
        bean.addPropertyValue("bus", busValue.getValue());
        try {
            Element elem = DOMUtils.getFirstElement(element);
            while (elem != null) {
                String name = elem.getLocalName();
                if ("tlsServerParameters".equals(name)) {
                    this.mapTLSServerParameters(elem, bean);
                } else if ("threadingParameters".equals(name)) {
                    this.mapElementToJaxbPropertyFactory(elem, bean, "threadingParameters", ThreadingParametersType.class, JettyHTTPServerEngineBeanDefinitionParser.class, "createThreadingParameters", new Object[0]);
                } else if ("tlsServerParametersRef".equals(name)) {
                    this.mapElementToJaxbPropertyFactory(elem, bean, "tlsServerParametersRef", TLSServerParametersIdentifiedType.class, JettyHTTPServerEngineBeanDefinitionParser.class, "createTLSServerParametersConfigRef", new Object[0]);
                } else if ("threadingParametersRef".equals(name)) {
                    this.mapElementToJaxbPropertyFactory(elem, bean, "threadingParametersRef", ThreadingParametersIdentifiedType.class, JettyHTTPServerEngineBeanDefinitionParser.class, "createThreadingParametersRef", new Object[0]);
                } else if ("connector".equals(name)) {
                    List list = ctx.getDelegate().parseListElement(elem, (BeanDefinition)bean.getBeanDefinition());
                    bean.addPropertyValue("connector", list.get(0));
                } else if ("handlers".equals(name)) {
                    List handlers = ctx.getDelegate().parseListElement(elem, (BeanDefinition)bean.getBeanDefinition());
                    bean.addPropertyValue("handlers", (Object)handlers);
                } else if ("sessionTimeout".equals(name) || "sessionSupport".equals(name) || "reuseAddress".equals(name)) {
                    String text = elem.getTextContent();
                    bean.addPropertyValue(name, (Object)text);
                }
                elem = DOMUtils.getNextElement(elem);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Could not process configuration.", e);
        }
        bean.setLazyInit(false);
    }

    private void mapTLSServerParameters(Element e, BeanDefinitionBuilder bean) {
        BeanDefinitionBuilder paramsbean = BeanDefinitionBuilder.rootBeanDefinition(TLSServerParametersConfig.TLSServerParametersTypeInternal.class);
        NamedNodeMap as = e.getAttributes();
        for (int i = 0; i < as.getLength(); ++i) {
            String aname;
            Attr a = (Attr)as.item(i);
            if (a.getNamespaceURI() != null || !"jsseProvider".equals(aname = a.getLocalName()) && !"secureSocketProtocol".equals(aname)) continue;
            paramsbean.addPropertyValue(aname, (Object)a.getValue());
        }
        Node n = e.getFirstChild();
        while (n != null) {
            if (1 != n.getNodeType() || !SECURITY_NS.equals(n.getNamespaceURI())) {
                n = n.getNextSibling();
                continue;
            }
            String ename = n.getLocalName();
            String ref = ((Element)n).getAttribute("ref");
            if ("keyManagers".equals(ename)) {
                if (ref != null && ref.length() > 0) {
                    paramsbean.addPropertyReference("keyManagersRef", ref);
                } else {
                    this.mapElementToJaxbProperty((Element)n, paramsbean, ename, KeyManagersType.class);
                }
            } else if ("trustManagers".equals(ename)) {
                if (ref != null && ref.length() > 0) {
                    paramsbean.addPropertyReference("trustManagersRef", ref);
                } else {
                    this.mapElementToJaxbProperty((Element)n, paramsbean, ename, TrustManagersType.class);
                }
            } else if ("cipherSuites".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, CipherSuites.class);
            } else if ("cipherSuitesFilter".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, FiltersType.class);
            } else if ("excludeProtocols".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, ExcludeProtocols.class);
            } else if ("includeProtocols".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, IncludeProtocols.class);
            } else if ("secureRandomParameters".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, SecureRandomParameters.class);
            } else if ("clientAuthentication".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, ClientAuthentication.class);
            } else if ("certConstraints".equals(ename)) {
                this.mapElementToJaxbProperty((Element)n, paramsbean, ename, CertificateConstraintsType.class);
            } else if ("certAlias".equals(ename)) {
                paramsbean.addPropertyValue(ename, (Object)n.getTextContent());
            }
            n = n.getNextSibling();
        }
        BeanDefinitionBuilder jaxbbean = BeanDefinitionBuilder.rootBeanDefinition(TLSServerParametersConfig.class);
        jaxbbean.addConstructorArgValue((Object)paramsbean.getBeanDefinition());
        bean.addPropertyValue("tlsServerParameters", (Object)jaxbbean.getBeanDefinition());
    }

    private static ThreadingParameters toThreadingParameters(ThreadingParametersType paramtype) {
        ThreadingParameters params = new ThreadingParameters();
        if (paramtype.getMaxThreads() != null) {
            params.setMaxThreads(paramtype.getMaxThreads());
        }
        if (paramtype.getMinThreads() != null) {
            params.setMinThreads(paramtype.getMinThreads());
        }
        if (paramtype.getThreadNamePrefix() != null) {
            params.setThreadNamePrefix(paramtype.getThreadNamePrefix());
        }
        return params;
    }

    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected Class<?> getBeanClass(Element arg0) {
        return SpringJettyHTTPServerEngine.class;
    }

    public static TLSServerParametersConfig createTLSServerParametersConfig(String s2, JAXBContext context) throws GeneralSecurityException, IOException {
        TLSServerParametersType parametersType = JettyHTTPServerEngineBeanDefinitionParser.unmarshalFactoryString(s2, context, TLSServerParametersType.class);
        return new TLSServerParametersConfig(parametersType);
    }

    public static String createTLSServerParametersConfigRef(String s2, JAXBContext context) throws GeneralSecurityException, IOException {
        TLSServerParametersIdentifiedType parameterTypeRef = JettyHTTPServerEngineBeanDefinitionParser.unmarshalFactoryString(s2, context, TLSServerParametersIdentifiedType.class);
        return parameterTypeRef.getId();
    }

    public static ThreadingParameters createThreadingParameters(String s2, JAXBContext context) {
        ThreadingParametersType parametersType = JettyHTTPServerEngineBeanDefinitionParser.unmarshalFactoryString(s2, context, ThreadingParametersType.class);
        return JettyHTTPServerEngineBeanDefinitionParser.toThreadingParameters(parametersType);
    }

    public static String createThreadingParametersRef(String s2, JAXBContext context) {
        ThreadingParametersIdentifiedType parametersType = JettyHTTPServerEngineBeanDefinitionParser.unmarshalFactoryString(s2, context, ThreadingParametersIdentifiedType.class);
        return parametersType.getId();
    }

    @NoJSR250Annotations
    public static class SpringJettyHTTPServerEngine
    extends JettyHTTPServerEngine
    implements ApplicationContextAware,
    InitializingBean {
        String threadingRef;
        String tlsRef;
        Bus bus;
        JettyHTTPServerEngineFactory factory;

        public SpringJettyHTTPServerEngine(JettyHTTPServerEngineFactory fac, Bus b, String host2, int port) {
            super(fac.getMBeanContainer(), host2, port);
            this.bus = b;
            this.factory = fac;
        }

        public SpringJettyHTTPServerEngine() {
        }

        public void setBus(Bus b) {
            this.bus = b;
            if (null != this.bus && null == this.factory) {
                this.factory = this.bus.getExtension(JettyHTTPServerEngineFactory.class);
            }
        }

        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
            if (this.bus == null) {
                this.bus = BusWiringBeanFactoryPostProcessor.addDefaultBus(ctx);
            }
        }

        public void setThreadingParametersRef(String s2) {
            this.threadingRef = s2;
        }

        public void setTlsServerParametersRef(String s2) {
            this.tlsRef = s2;
        }

        @Override
        @PostConstruct
        public void finalizeConfig() throws GeneralSecurityException, IOException {
            if (this.tlsRef != null || this.threadingRef != null) {
                if (this.threadingRef != null) {
                    this.setThreadingParameters(this.factory.getThreadingParametersMap().get(this.threadingRef));
                }
                if (this.tlsRef != null) {
                    this.setTlsServerParameters(this.factory.getTlsServerParametersMap().get(this.tlsRef));
                }
            }
            super.finalizeConfig();
        }

        public void afterPropertiesSet() throws Exception {
            this.finalizeConfig();
        }
    }
}

