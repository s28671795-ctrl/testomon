/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 */
package org.apache.cxf.service.invoker.spring;

import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.Factory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanFactory
implements Factory,
ApplicationContextAware {
    volatile ApplicationContext ctx;
    final String beanName;

    public SpringBeanFactory(String name) {
        this.beanName = name;
    }

    public SpringBeanFactory(Class<?> c, String[] args2) {
        this.beanName = args2[0];
    }

    @Override
    public Object create(Exchange e) throws Throwable {
        if (this.ctx == null) {
            this.ctx = e.getBus().getExtension(ApplicationContext.class);
        }
        return this.ctx.getBean(this.beanName);
    }

    @Override
    public void release(Exchange e, Object o) {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }
}

