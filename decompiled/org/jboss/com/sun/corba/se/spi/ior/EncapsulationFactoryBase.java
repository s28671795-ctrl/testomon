/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.impl.ior.EncapsulationUtility;
import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.omg.CORBA_2_3.portable.InputStream;

public abstract class EncapsulationFactoryBase
implements IdentifiableFactory {
    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    public EncapsulationFactoryBase(int id) {
        this.id = id;
    }

    @Override
    public final Identifiable create(InputStream in) {
        InputStream is = EncapsulationUtility.getEncapsulationStream(in);
        return this.readContents(is);
    }

    protected abstract Identifiable readContents(InputStream var1);
}

