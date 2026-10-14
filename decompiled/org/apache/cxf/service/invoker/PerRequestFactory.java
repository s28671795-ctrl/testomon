/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import java.lang.reflect.Modifier;
import java.util.ResourceBundle;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.ResourceInjector;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.service.invoker.Factory;

public class PerRequestFactory
implements Factory {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(PerRequestFactory.class);
    private final Class<?> svcClass;

    public PerRequestFactory(Class<?> svcClass) {
        this.svcClass = svcClass;
    }

    @Override
    public Object create(Exchange ex) throws Throwable {
        try {
            if (this.svcClass.isInterface()) {
                throw new Fault(new Message("SVC_CLASS_IS_INTERFACE", BUNDLE, this.svcClass.getName()));
            }
            if (Modifier.isAbstract(this.svcClass.getModifiers())) {
                throw new Fault(new Message("SVC_CLASS_IS_ABSTRACT", BUNDLE, this.svcClass.getName()));
            }
            Object o = this.svcClass.newInstance();
            Bus b = ex.getBus();
            ResourceManager resourceManager = b.getExtension(ResourceManager.class);
            if (resourceManager != null) {
                ResourceInjector injector = new ResourceInjector(resourceManager);
                injector.inject(o);
                injector.construct(o);
            }
            return o;
        }
        catch (InstantiationException e) {
            throw new Fault(new Message("COULD_NOT_INSTANTIATE", BUNDLE, new Object[0]), (Throwable)e);
        }
        catch (IllegalAccessException e) {
            throw new Fault(new Message("ILLEGAL_ACCESS", BUNDLE, new Object[0]), (Throwable)e);
        }
    }

    @Override
    public void release(Exchange ex, Object o) {
    }
}

