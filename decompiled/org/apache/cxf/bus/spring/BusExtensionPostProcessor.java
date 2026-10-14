/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.Ordered
 */
package org.apache.cxf.bus.spring;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.extension.BusExtension;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

@NoJSR250Annotations
public class BusExtensionPostProcessor
implements BeanPostProcessor,
ApplicationContextAware,
Ordered {
    private Bus bus;
    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext ctx) {
        this.context = ctx;
    }

    public int getOrder() {
        return 1001;
    }

    public Object postProcessAfterInitialization(Object bean, String beanId) {
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanId) {
        if (bean instanceof BusExtension && null != this.getBus()) {
            Class<?> cls = ((BusExtension)bean).getRegistrationType();
            this.registerExt(bean, cls);
        } else if (bean instanceof Bus && "cxf".equals(beanId)) {
            this.bus = (Bus)bean;
        }
        return bean;
    }

    private <T> void registerExt(Object bean, Class<T> cls) {
        this.getBus().setExtension(cls.cast(bean), cls);
    }

    private Bus getBus() {
        if (this.bus == null) {
            this.bus = (Bus)this.context.getBean("cxf");
        }
        return this.bus;
    }
}

