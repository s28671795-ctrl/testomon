/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.ValueHandler;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public interface UtilDelegate {
    public RemoteException mapSystemException(SystemException var1);

    public void writeAny(OutputStream var1, Object var2);

    public Object readAny(InputStream var1);

    public void writeRemoteObject(OutputStream var1, Object var2);

    public void writeAbstractObject(OutputStream var1, Object var2);

    public void registerTarget(Tie var1, Remote var2);

    public void unexportObject(Remote var1) throws NoSuchObjectException;

    public Tie getTie(Remote var1);

    public ValueHandler createValueHandler();

    public String getCodebase(Class<?> var1);

    public Class<?> loadClass(String var1, String var2, ClassLoader var3) throws ClassNotFoundException;

    public boolean isLocal(Stub var1) throws RemoteException;

    public RemoteException wrapException(Throwable var1);

    public Object copyObject(Object var1, ORB var2) throws RemoteException;

    public Object[] copyObjects(Object[] var1, ORB var2) throws RemoteException;
}

