/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.frontend.spring;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class ServerFactoryBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    public ServerFactoryBeanDefinitionParser() {
        this.setBeanClass(SpringServerFactoryBean.class);
    }

    @Override
    protected void mapAttribute(BeanDefinitionBuilder bean, Element e, String name, String val) {
        if ("endpointName".equals(name) || "serviceName".equals(name)) {
            QName q = this.parseQName(e, val);
            bean.addPropertyValue(name, (Object)q);
        } else {
            this.mapToProperty(bean, name, val);
        }
    }

    @Override
    protected boolean parseAttribute(Element element, Attr node, ParserContext ctx, BeanDefinitionBuilder bean) {
        if (!node.getSpecified() && "start".equals(node.getLocalName())) {
            return false;
        }
        return super.parseAttribute(element, node, ctx, bean);
    }

    @Override
    protected void mapElement(ParserContext ctx, BeanDefinitionBuilder bean, Element el, String name) {
        if ("properties".equals(name)) {
            Map map = ctx.getDelegate().parseMapElement(el, (BeanDefinition)bean.getBeanDefinition());
            bean.addPropertyValue("properties", (Object)map);
        } else if ("executor".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, "serviceFactory.executor");
        } else if ("invoker".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, "serviceFactory.invoker");
        } else if ("binding".equals(name)) {
            this.setFirstChildAsProperty(el, ctx, bean, "bindingConfig");
        } else if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name) || "schemaLocations".equals(name)) {
            List list = ctx.getDelegate().parseListElement(el, (BeanDefinition)bean.getBeanDefinition());
            bean.addPropertyValue(name, (Object)list);
        } else {
            this.setFirstChildAsProperty(el, ctx, bean, name);
        }
    }

    @Override
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        super.doParse(element, ctx, bean);
        bean.setInitMethodName("create");
        bean.setDestroyMethodName("destroy");
        bean.setLazyInit(false);
    }

    @Override
    protected String resolveId(Element elem, AbstractBeanDefinition definition, ParserContext ctx) throws BeanDefinitionStoreException {
        String id = super.resolveId(elem, definition, ctx);
        if (StringUtils.isEmpty(id)) {
            id = this.getBeanClass().getName() + "--" + definition.hashCode();
        }
        return id;
    }

    @Override
    protected boolean hasBusProperty() {
        return true;
    }

    @NoJSR250Annotations
    public static class SpringServerFactoryBean
    extends ServerFactoryBean
    implements ApplicationContextAware {
        private Server server;

        public SpringServerFactoryBean() {
        }

        public SpringServerFactoryBean(ReflectionServiceFactoryBean fact) {
            super(fact);
        }

        @Override
        public Server getServer() {
            return this.server;
        }

        public void init() {
            this.create();
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

