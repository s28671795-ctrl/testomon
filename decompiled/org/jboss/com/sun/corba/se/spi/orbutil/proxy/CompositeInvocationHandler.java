/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;

public interface CompositeInvocationHandler
extends InvocationHandler,
Serializable {
    public void addInvocationHandler(Class<?> var1, InvocationHandler var2);

    public void setDefaultHandler(InvocationHandler var1);
}

