/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.xml.ws.Response;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.factory.SimpleMethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;

public class JAXWSMethodDispatcher
extends SimpleMethodDispatcher {
    private static final Logger LOG = LogUtils.getL7dLogger(JAXWSMethodDispatcher.class);
    private JaxWsImplementorInfo implInfo;

    public JAXWSMethodDispatcher(JaxWsImplementorInfo implInfo) {
        this.implInfo = implInfo;
    }

    @Override
    public void bind(OperationInfo o, Method ... methods) {
        Method[] newMethods = new Method[methods.length];
        int i = 0;
        for (Method m4 : methods) {
            try {
                newMethods[i] = this.getImplementationMethod(m4);
                ++i;
            }
            catch (NoSuchMethodException e) {
                if (m4.getName().endsWith("Async") && (Future.class.equals(m4.getReturnType()) || Response.class.equals(m4.getReturnType()))) {
                    newMethods[i] = m4;
                    ++i;
                    continue;
                }
                Class<?> endpointClass = this.implInfo.getImplementorClass();
                Message msg = new Message("SEI_METHOD_NOT_FOUND", LOG, m4.getName(), endpointClass.getName());
                throw new ServiceConstructionException(msg, (Throwable)e);
            }
        }
        super.bind(o, newMethods);
    }

    @Override
    public BindingOperationInfo getBindingOperation(Method method2, Endpoint endpoint) {
        try {
            method2 = this.getImplementationMethod(method2);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        return super.getBindingOperation(method2, endpoint);
    }

    public Method getImplementationMethod(Method method2) throws NoSuchMethodException {
        Class<?> endpointClass = this.implInfo.getImplementorClass();
        if (!endpointClass.isAssignableFrom(method2.getDeclaringClass())) {
            try {
                Method m22 = endpointClass.getMethod(method2.getName(), method2.getParameterTypes());
                if (Modifier.isVolatile(m22.getModifiers())) {
                    Class<?>[] params = method2.getParameterTypes();
                    for (Type t2 : method2.getGenericParameterTypes()) {
                        if (!(t2 instanceof TypeVariable)) continue;
                        TypeVariable tv = (TypeVariable)t2;
                        for (int x = 0; x < this.implInfo.getSEIClass().getTypeParameters().length; ++x) {
                            TypeVariable<Class<?>> t22 = this.implInfo.getSEIClass().getTypeParameters()[x];
                            if (!t22.getName().equals(tv.getName())) continue;
                            params[x] = (Class)this.implInfo.getSEIType().getActualTypeArguments()[x];
                        }
                    }
                    method2 = endpointClass.getMethod(method2.getName(), params);
                } else {
                    method2 = m22;
                }
                try {
                    ReflectionUtil.setAccessible(method2);
                }
                catch (Throwable throwable) {}
            }
            catch (SecurityException e) {
                throw new ServiceConstructionException(e);
            }
        }
        return method2;
    }
}

