/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PortableRemoteObjectDelegate {
    public void exportObject(Remote var1) throws RemoteException;

    public Remote toStub(Remote var1) throws NoSuchObjectException;

    public void unexportObject(Remote var1) throws NoSuchObjectException;

    public Object narrow(Object var1, Class<?> var2) throws ClassCastException;

    public void connect(Remote var1, Remote var2) throws RemoteException;
}

