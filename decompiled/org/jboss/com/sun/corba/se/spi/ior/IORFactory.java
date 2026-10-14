/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.MakeImmutable;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.Writeable;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public interface IORFactory
extends Writeable,
MakeImmutable {
    public IOR makeIOR(ORB var1, String var2, ObjectId var3);

    public boolean isEquivalent(IORFactory var1);
}

