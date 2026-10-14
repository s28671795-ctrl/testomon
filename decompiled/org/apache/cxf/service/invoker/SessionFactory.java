/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.invoker.FactoryInvoker;
import org.apache.cxf.service.invoker.PerRequestFactory;

public class SessionFactory
implements Factory {
    Factory factory;
    boolean createIfNotExist;

    public SessionFactory(Class<?> svcClass) {
        this(new PerRequestFactory(svcClass), true);
    }

    public SessionFactory(Factory f) {
        this(f, true);
    }

    public SessionFactory(Class<?> svcClass, boolean createOnDemand) {
        this(new PerRequestFactory(svcClass), createOnDemand);
    }

    public SessionFactory(Factory f, boolean createOnDemand) {
        this.factory = f;
        this.createIfNotExist = createOnDemand;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object create(Exchange e) throws Throwable {
        Object o;
        Service serv;
        Service service2 = serv = e.getService();
        synchronized (service2) {
            o = e.getSession().get(serv.getName().toString());
            if (o == null) {
                if (this.createIfNotExist) {
                    o = this.factory.create(e);
                    e.getSession().put(serv.getName().toString(), o);
                } else {
                    throw new Fault("COULD_NOT_INSTANTIATE", FactoryInvoker.BUNDLE);
                }
            }
        }
        return o;
    }

    @Override
    public void release(Exchange e, Object o) {
    }
}

