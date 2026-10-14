/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.bind.JAXBElement;
import org.apache.cxf.databinding.AbstractWrapperHelper;

public class JAXBWrapperHelper
extends AbstractWrapperHelper {
    protected final Method[] jaxbObjectMethods;
    protected final Object objectFactory;

    protected JAXBWrapperHelper(Class<?> wt, Method[] sets, Method[] gets, Method[] jaxbs, Field[] f, Object of) {
        super(wt, sets, gets, f);
        this.jaxbObjectMethods = jaxbs;
        this.objectFactory = of;
    }

    @Override
    protected Object createWrapperObject(Class<?> typeClass) throws Exception {
        return typeClass.newInstance();
    }

    @Override
    protected Object getWrapperObject(Object object) {
        return object;
    }

    @Override
    protected Object getValue(Method method2, Object in) throws IllegalAccessException, InvocationTargetException {
        if ("javax.xml.bind.JAXBElement".equals(method2.getReturnType().getCanonicalName())) {
            JAXBElement je = (JAXBElement)method2.invoke(in, new Object[0]);
            return je == null ? je : je.getValue();
        }
        return method2.invoke(in, new Object[0]);
    }

    @Override
    protected Object getPartObject(int index, Object object) throws Exception {
        Object result = object;
        if (this.jaxbObjectMethods[index] != null) {
            result = this.jaxbObjectMethods[index].invoke(this.objectFactory, object);
        }
        return result;
    }
}

