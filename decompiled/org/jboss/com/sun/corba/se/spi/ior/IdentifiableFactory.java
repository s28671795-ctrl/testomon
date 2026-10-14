/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.omg.CORBA_2_3.portable.InputStream;

public interface IdentifiableFactory {
    public int getId();

    public Identifiable create(InputStream var1);
}

