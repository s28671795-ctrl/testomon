/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import java.util.Iterator;
import java.util.List;
import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.jboss.com.sun.corba.se.spi.ior.MakeImmutable;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfile;
import org.jboss.com.sun.corba.se.spi.ior.WriteContents;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.IOP.TaggedComponent;

public interface TaggedProfileTemplate
extends List<Object>,
Identifiable,
WriteContents,
MakeImmutable {
    public Iterator<Object> iteratorById(int var1);

    public TaggedProfile create(ObjectKeyTemplate var1, ObjectId var2);

    public void write(ObjectKeyTemplate var1, ObjectId var2, OutputStream var3);

    public boolean isEquivalent(TaggedProfileTemplate var1);

    public TaggedComponent[] getIOPComponents(ORB var1, int var2);
}

