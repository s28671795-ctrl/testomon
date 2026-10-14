/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.jaxws.spring;

import java.io.Closeable;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.spring.ClientProxyFactoryBeanDefinitionParser;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class JaxWsProxyFactoryBeanDefinitionParser
extends ClientProxyFactoryBeanDefinitionParser {
    public JaxWsProxyFactoryBeanDefinitionParser() {
        this.setBeanClass(JAXWSSpringClientProxyFactoryBean.class);
    }

    @Override
    protected Class<?> getRawFactoryClass() {
        return JaxWsProxyFactoryBean.class;
    }

    @Override
    protected Class<?> getFactoryClass() {
        return JAXWSSpringClientProxyFactoryBean.class;
    }

    @Override
    protected String getSuffix() {
        return ".jaxws-client";
    }

    @NoJSR250Annotations
    public static class JAXWSSpringClientProxyFactoryBean
    extends JaxWsProxyFactoryBean
    implements ApplicationContextAware,
    FactoryBean<Object>,
    DisposableBean {
        private Object obj;

        public JAXWSSpringClientProxyFactoryBean() {
        }

        public JAXWSSpringClientProxyFactoryBean(ClientFactoryBean fact) {
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

