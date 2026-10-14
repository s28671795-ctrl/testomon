/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.invoker.PerRequestFactory;

public class SingletonFactory
implements Factory {
    Object bean;
    Factory factory;

    public SingletonFactory(Object bean) {
        this.bean = bean;
    }

    public SingletonFactory(Class<?> beanClass) {
        this.factory = new PerRequestFactory(beanClass);
    }

    public SingletonFactory(Factory f) {
        this.factory = f;
    }

    @Override
    public Object create(Exchange ex) throws Throwable {
        if (this.bean == null && this.factory != null) {
            this.createBean(ex);
        }
        return this.bean;
    }

    private synchronized void createBean(Exchange e) throws Throwable {
        if (this.bean == null) {
            this.bean = this.factory.create(e);
        }
    }

    @Override
    public void release(Exchange ex, Object o) {
    }
}

