/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.oa;

import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.oa.OADestroyed;
import org.jboss.com.sun.corba.se.spi.oa.OAInvocationInfo;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;

public interface ObjectAdapter {
    public ORB getORB();

    public Policy getEffectivePolicy(int var1);

    public IORTemplate getIORTemplate();

    public int getManagerId();

    public short getState();

    public ObjectReferenceTemplate getAdapterTemplate();

    public ObjectReferenceFactory getCurrentFactory();

    public void setCurrentFactory(ObjectReferenceFactory var1);

    public Object getLocalServant(byte[] var1);

    public void getInvocationServant(OAInvocationInfo var1);

    public void enter() throws OADestroyed;

    public void exit();

    public void returnServant();

    public OAInvocationInfo makeInvocationInfo(byte[] var1);

    public String[] getInterfaces(java.lang.Object var1, byte[] var2);
}

