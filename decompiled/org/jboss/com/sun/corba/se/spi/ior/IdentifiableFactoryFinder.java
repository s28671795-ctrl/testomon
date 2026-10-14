/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.omg.CORBA_2_3.portable.InputStream;

public interface IdentifiableFactoryFinder {
    public Identifiable create(int var1, InputStream var2);

    public void registerFactory(IdentifiableFactory var1);
}

