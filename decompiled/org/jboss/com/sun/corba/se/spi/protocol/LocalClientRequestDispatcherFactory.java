/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;

public interface LocalClientRequestDispatcherFactory {
    public LocalClientRequestDispatcher create(int var1, IOR var2);
}

