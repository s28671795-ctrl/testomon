/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.transport;

import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.transport.CorbaContactInfoList;

public interface CorbaContactInfoListFactory {
    public void setORB(ORB var1);

    public CorbaContactInfoList create(IOR var1);
}

