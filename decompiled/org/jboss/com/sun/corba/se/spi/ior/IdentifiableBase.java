/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.impl.ior.EncapsulationUtility;
import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.jboss.com.sun.corba.se.spi.ior.WriteContents;
import org.omg.CORBA_2_3.portable.OutputStream;

public abstract class IdentifiableBase
implements Identifiable,
WriteContents {
    @Override
    public final void write(OutputStream os) {
        EncapsulationUtility.writeEncapsulation(this, os);
    }
}

