/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public interface LinkedInvocationHandler
extends InvocationHandler {
    public void setProxy(Proxy var1);

    public Proxy getProxy();
}

