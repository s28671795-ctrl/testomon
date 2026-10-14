/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.resolver;

import org.jboss.com.sun.corba.se.spi.orbutil.closure.Closure;
import org.jboss.com.sun.corba.se.spi.resolver.Resolver;

public interface LocalResolver
extends Resolver {
    public void register(String var1, Closure var2);
}

