/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import java.util.ResourceBundle;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.AbstractInvoker;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.invoker.SingletonFactory;

public class FactoryInvoker
extends AbstractInvoker {
    static final ResourceBundle BUNDLE = BundleUtils.getBundle(FactoryInvoker.class);
    protected Factory factory;

    public FactoryInvoker(Factory factory2) {
        this.factory = factory2;
    }

    public FactoryInvoker() {
    }

    public void setFactory(Factory f) {
        this.factory = f;
    }

    @Override
    public Object getServiceObject(Exchange ex) {
        try {
            return this.factory.create(ex);
        }
        catch (Fault e) {
            throw e;
        }
        catch (Throwable e) {
            throw new Fault(new Message("CREATE_SERVICE_OBJECT_EXC", BUNDLE, new Object[0]), e);
        }
    }

    @Override
    public void releaseServiceObject(Exchange ex, Object obj) {
        this.factory.release(ex, obj);
    }

    public boolean isSingletonFactory() {
        return this.factory instanceof SingletonFactory;
    }
}

