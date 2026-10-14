/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.resolver;

import java.util.Set;
import org.omg.CORBA.Object;

public interface Resolver {
    public Object resolve(String var1);

    public Set<java.lang.Object> list();
}

