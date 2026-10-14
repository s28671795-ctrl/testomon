/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 *  org.springframework.beans.factory.xml.NamespaceHandlerSupport
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.jaxws.spring;

import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.spring.StringBeanDefinitionParser;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.spring.ServerFactoryBeanDefinitionParser;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.spring.EndpointDefinitionParser;
import org.apache.cxf.jaxws.spring.JaxWsProxyFactoryBeanDefinitionParser;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class NamespaceHandler
extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("client", (BeanDefinitionParser)new JaxWsProxyFactoryBeanDefinitionParser());
        this.registerBeanDefinitionParser("endpoint", (BeanDefinitionParser)new EndpointDefinitionParser());
        this.registerBeanDefinitionParser("schemaLocation", (BeanDefinitionParser)new StringBeanDefinitionParser());
        ServerFactoryBeanDefinitionParser parser = new ServerFactoryBeanDefinitionParser();
        parser.setBeanClass(SpringServerFactoryBean.class);
        this.registerBeanDefinitionParser("server", (BeanDefinitionParser)parser);
    }

    @NoJSR250Annotations
    public static class SpringServerFactoryBean
    extends JaxWsServerFactoryBean
    implements ApplicationContextAware {
        Server server;

        public SpringServerFactoryBean() {
        }

        public SpringServerFactoryBean(JaxWsServiceFactoryBean fact) {
            super(fact);
        }

        @Override
        public Server getServer() {
            return this.server;
        }

        @Override
        public Server create() {
            if (this.server == null) {
                this.server = super.create();
            }
            return this.server;
        }

        public void destroy() {
            if (this.server != null) {
                this.server.destroy();
                this.server = null;
            }
        }

        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
            if (this.bus == null) {
                this.setBus(BusWiringBeanFactoryPostProcessor.addDefaultBus(ctx));
            }
        }
    }
}

