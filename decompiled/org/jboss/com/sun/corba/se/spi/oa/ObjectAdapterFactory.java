/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.oa;

import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapter;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public interface ObjectAdapterFactory {
    public void init(ORB var1);

    public void shutdown(boolean var1);

    public ObjectAdapter find(ObjectAdapterId var1);

    public ORB getORB();
}

