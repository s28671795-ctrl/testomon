/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.omg.CORBA.ORB;

public interface TaggedComponent
extends Identifiable {
    public org.omg.IOP.TaggedComponent getIOPComponent(ORB var1);
}

