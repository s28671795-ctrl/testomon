/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.transport.http_jetty.spring;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.configuration.spring.BusWiringType;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.apache.cxf.transport.http_jetty.ThreadingParameters;
import org.apache.cxf.transport.http_jetty.spring.JettySpringTypesFactory;
import org.apache.cxf.transports.http_jetty.configuration.TLSServerParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersIdentifiedType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Element;

public class JettyHTTPServerEngineFactoryBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    static final String HTTP_JETTY_NS = "http://cxf.apache.org/transports/http-jetty/configuration";

    @Override
    protected String resolveId(Element elem, AbstractBeanDefinition definition, ParserContext ctx) throws BeanDefinitionStoreException {
        String id = this.getIdOrName(elem);
        if (StringUtils.isEmpty(id)) {
            return JettyHTTPServerEngineFactory.class.getName();
        }
        id = super.resolveId(elem, definition, ctx);
        if (!ctx.getRegistry().containsBeanDefinition(JettyHTTPServerEngineFactory.class.getName())) {
            ctx.getRegistry().registerAlias(id, JettyHTTPServerEngineFactory.class.getName());
        }
        return id;
    }

    @Override
    public void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        String bus = element.getAttribute("bus");
        BeanDefinitionBuilder factbean = BeanDefinitionBuilder.rootBeanDefinition(JettySpringTypesFactory.class);
        ctx.getRegistry().registerBeanDefinition(JettySpringTypesFactory.class.getName(), (BeanDefinition)factbean.getBeanDefinition());
        try {
            if (StringUtils.isEmpty(bus)) {
                this.addBusWiringAttribute(bean, BusWiringType.CONSTRUCTOR);
            } else {
                bean.addConstructorArgReference(bus);
            }
            bean.addConstructorArgValue((Object)this.mapElementToJaxbBean(element, TLSServerParametersIdentifiedType.class, JettySpringTypesFactory.class, "createTLSServerParametersMap", new Object[0]));
            bean.addConstructorArgValue((Object)this.mapElementToJaxbBean(element, ThreadingParametersIdentifiedType.class, JettySpringTypesFactory.class, "createThreadingParametersMap", new Object[0]));
            List<Object> list = this.getRequiredElementsList(element, ctx, new QName(HTTP_JETTY_NS, "engine"), bean);
            if (!list.isEmpty()) {
                bean.addPropertyValue("enginesList", list);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Could not process configuration.", e);
        }
    }

    private List<Object> getRequiredElementsList(Element parent, ParserContext ctx, QName name, BeanDefinitionBuilder bean) {
        List<Element> elemList = DOMUtils.findAllElementsByTagNameNS(parent, name.getNamespaceURI(), name.getLocalPart());
        ManagedList list = new ManagedList(elemList.size());
        list.setSource(ctx.extractSource((Object)parent));
        for (Element elem : elemList) {
            list.add(ctx.getDelegate().parsePropertySubElement(elem, (BeanDefinition)bean.getBeanDefinition()));
        }
        return list;
    }

    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected Class<?> getBeanClass(Element arg0) {
        return SpringJettyHTTPServerEngineFactory.class;
    }

    @NoJSR250Annotations(unlessNull={"bus"})
    public static class SpringJettyHTTPServerEngineFactory
    extends JettyHTTPServerEngineFactory
    implements ApplicationContextAware {
        public SpringJettyHTTPServerEngineFactory() {
        }

        public SpringJettyHTTPServerEngineFactory(Bus bus, Map<String, TLSServerParameters> tls, Map<String, ThreadingParameters> threading) {
            super(bus, tls, threading);
        }

        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
            if (this.getBus() == null) {
                this.setBus(BusWiringBeanFactoryPostProcessor.addDefaultBus(ctx));
            }
        }
    }
}

