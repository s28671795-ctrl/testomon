/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.proxy;

import java.lang.reflect.InvocationHandler;

public interface InvocationHandlerFactory {
    public InvocationHandler getInvocationHandler();

    public Class<?>[] getProxyInterfaces();
}

