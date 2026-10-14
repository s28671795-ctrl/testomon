/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.lang.reflect.Method;
import java.util.Comparator;

class MethodComparator
implements Comparator<Method> {
    MethodComparator() {
    }

    @Override
    public int compare(Method m1, Method m22) {
        int val = m1.getName().compareTo(m22.getName());
        if (val == 0 && (val = m1.getParameterTypes().length - m22.getParameterTypes().length) == 0) {
            Class<?>[] types1 = m1.getParameterTypes();
            Class<?>[] types2 = m22.getParameterTypes();
            for (int i = 0; i < types1.length && (val = types1[i].getName().compareTo(types2[i].getName())) == 0; ++i) {
            }
        }
        return val;
    }
}

