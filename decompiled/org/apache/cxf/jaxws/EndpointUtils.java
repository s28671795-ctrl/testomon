/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.ws.Provider;
import javax.xml.ws.WebServiceProvider;
import org.apache.cxf.common.logging.LogUtils;

public final class EndpointUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(EndpointUtils.class);

    private EndpointUtils() {
    }

    public static boolean hasWebServiceAnnotation(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        if (null != cls.getAnnotation(WebService.class)) {
            return true;
        }
        for (Class<?> inf : cls.getInterfaces()) {
            if (null == inf.getAnnotation(WebService.class)) continue;
            return true;
        }
        return EndpointUtils.hasWebServiceAnnotation(cls.getSuperclass());
    }

    private static boolean hasWebServiceProviderAnnotation(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        if (null != cls.getAnnotation(WebServiceProvider.class)) {
            return true;
        }
        for (Class<?> inf : cls.getInterfaces()) {
            if (null == inf.getAnnotation(WebServiceProvider.class)) continue;
            return true;
        }
        return EndpointUtils.hasWebServiceProviderAnnotation(cls.getSuperclass());
    }

    public static boolean isValidImplementor(Object implementor) {
        return EndpointUtils.isValidImplementor(implementor.getClass());
    }

    public static boolean isValidImplementor(Class<?> implementorClass) {
        if (Provider.class.isAssignableFrom(implementorClass) && EndpointUtils.hasWebServiceProviderAnnotation(implementorClass)) {
            return true;
        }
        if (EndpointUtils.hasWebServiceAnnotation(implementorClass)) {
            return true;
        }
        LOG.info("Implementor is not annotated with WebService annotation.");
        return false;
    }
}

