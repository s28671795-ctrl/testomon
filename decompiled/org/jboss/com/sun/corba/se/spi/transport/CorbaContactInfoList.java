/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.transport;

import org.jboss.com.sun.corba.se.pept.transport.ContactInfoList;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;

public interface CorbaContactInfoList
extends ContactInfoList {
    public void setTargetIOR(IOR var1);

    public IOR getTargetIOR();

    public void setEffectiveTargetIOR(IOR var1);

    public IOR getEffectiveTargetIOR();

    public LocalClientRequestDispatcher getLocalClientRequestDispatcher();

    public int hashCode();
}

