/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.TaggedProfile;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersion;

public interface IIOPProfile
extends TaggedProfile {
    public ORBVersion getORBVersion();

    public Object getServant();

    public GIOPVersion getGIOPVersion();

    public String getCodebase();
}

