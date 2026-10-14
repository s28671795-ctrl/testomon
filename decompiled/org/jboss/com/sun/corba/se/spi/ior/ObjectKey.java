/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.Writeable;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.omg.CORBA.ORB;

public interface ObjectKey
extends Writeable {
    public ObjectId getId();

    public ObjectKeyTemplate getTemplate();

    public byte[] getBytes(ORB var1);

    public CorbaServerRequestDispatcher getServerRequestDispatcher(org.jboss.com.sun.corba.se.spi.orb.ORB var1);
}

