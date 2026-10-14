/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.presentation.rmi;

import java.rmi.RemoteException;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.OutputStream;

public interface DynamicStub
extends Object {
    public void setDelegate(Delegate var1);

    public Delegate getDelegate();

    public ORB getORB();

    public String[] getTypeIds();

    public void connect(ORB var1) throws RemoteException;

    public boolean isLocal();

    public OutputStream request(String var1, boolean var2);
}

