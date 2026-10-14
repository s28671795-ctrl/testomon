/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.frontend.spring;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.spring.AbstractFactoryBeanDefinitionParser;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Element;

public class ClientProxyFactoryBeanDefinitionParser
extends AbstractFactoryBeanDefinitionParser {
    public ClientProxyFactoryBeanDefinitionParser() {
        this.setBeanClass(SpringClientProxyFactoryBean.class);
    }

    @Override
    protected Class<?> getFactoryClass() {
        return SpringClientProxyFactoryBean.class;
    }

    @Override
    protected Class<?> getRawFactoryClass() {
        return ClientProxyFactoryBean.class;
    }

    @Override
    protected String getFactoryIdSuffix() {
        return ".proxyFactory";
    }

    @Override
    protected String getSuffix() {
        return ".simple-client";
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
    protected void mapElement(ParserContext ctx, BeanDefinitionBuilder bean, Element e, String name) {
        if ("properties".equals(name)) {
            Map map = ctx.getDelegate().parseMapElement(e, (BeanDefinition)bean.getBeanDefinition());
            bean.addPropertyValue("properties", (Object)map);
        } else if ("binding".equals(name)) {
            this.setFirstChildAsProperty(e, ctx, bean, "bindingConfig");
        } else if ("inInterceptors".equals(name) || "inFaultInterceptors".equals(name) || "outInterceptors".equals(name) || "outFaultInterceptors".equals(name) || "features".equals(name) || "handlers".equals(name)) {
            List list = ctx.getDelegate().parseListElement(e, (BeanDefinition)bean.getBeanDefinition());
            bean.addPropertyValue(name, (Object)list);
        } else {
            this.setFirstChildAsProperty(e, ctx, bean, name);
        }
    }

    @NoJSR250Annotations
    public static class SpringClientProxyFactoryBean
    extends ClientProxyFactoryBean
    implements ApplicationContextAware,
    FactoryBean<Object>,
    DisposableBean {
        private Object obj;

        public SpringClientProxyFactoryBean() {
        }

        public SpringClientProxyFactoryBean(ClientFactoryBean fact) {
            super(fact);
        }

        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
            if (this.getBus() == null) {
                this.setBus(BusWiringBeanFactoryPostProcessor.addDefaultBus(ctx));
            }
        }

        @Override
        public Object create() {
            this.configured = true;
            return super.create();
        }

        public synchronized Object getObject() throws Exception {
            if (this.obj == null) {
                this.obj = this.create();
            }
            return this.obj;
        }

        public Class<?> getObjectType() {
            return this.getServiceClass();
        }

        public boolean isSingleton() {
            return true;
        }

        public void destroy() throws Exception {
            if (this.obj != null) {
                if (this.obj instanceof Closeable) {
                    ((Closeable)this.obj).close();
                } else {
                    Client c = ClientProxy.getClient(this.obj);
                    c.destroy();
                }
                this.obj = null;
            }
        }
    }
}

