/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import java.util.Iterator;
import java.util.List;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.ior.MakeImmutable;
import org.jboss.com.sun.corba.se.spi.ior.Writeable;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public interface IOR
extends List<Object>,
Writeable,
MakeImmutable {
    public ORB getORB();

    public String getTypeId();

    public Iterator<Object> iteratorById(int var1);

    public String stringify();

    public org.omg.IOP.IOR getIOPIOR();

    public boolean isNil();

    public boolean isEquivalent(IOR var1);

    public IORTemplateList getIORTemplates();

    public IIOPProfile getProfile();
}

