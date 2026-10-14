/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.Writeable;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.omg.CORBA_2_3.portable.OutputStream;

public interface ObjectKeyTemplate
extends Writeable {
    public ORBVersion getORBVersion();

    public int getSubcontractId();

    public int getServerId();

    public String getORBId();

    public ObjectAdapterId getObjectAdapterId();

    public byte[] getAdapterId();

    public void write(ObjectId var1, OutputStream var2);

    public CorbaServerRequestDispatcher getServerRequestDispatcher(ORB var1, ObjectId var2);
}

