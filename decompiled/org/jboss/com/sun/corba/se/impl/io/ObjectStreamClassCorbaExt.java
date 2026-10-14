/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

class ObjectStreamClassCorbaExt {
    ObjectStreamClassCorbaExt() {
    }

    static final boolean isAbstractInterface(Class<?> cl) {
        if (!cl.isInterface() || Remote.class.isAssignableFrom(cl)) {
            return false;
        }
        Method[] methods = cl.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            Class<?>[] exceptions = methods[i].getExceptionTypes();
            boolean exceptionMatch = false;
            for (int j = 0; j < exceptions.length && !exceptionMatch; ++j) {
                if (RemoteException.class != exceptions[j] && Throwable.class != exceptions[j] && Exception.class != exceptions[j] && IOException.class != exceptions[j]) continue;
                exceptionMatch = true;
            }
            if (exceptionMatch) continue;
            return false;
        }
        return true;
    }

    static final boolean isAny(String typeString) {
        boolean isAny = false;
        if (typeString != null && (typeString.equals("Ljava/lang/Object;") || typeString.equals("Ljava/io/Serializable;") || typeString.equals("Ljava/io/Externalizable;"))) {
            isAny = true;
        }
        return isAny;
    }
}

