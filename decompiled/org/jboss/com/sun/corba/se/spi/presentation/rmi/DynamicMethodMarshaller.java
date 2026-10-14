/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.presentation.rmi;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public interface DynamicMethodMarshaller {
    public Method getMethod();

    public Object[] copyArguments(Object[] var1, ORB var2) throws RemoteException;

    public Object[] readArguments(InputStream var1);

    public void writeArguments(OutputStream var1, Object[] var2);

    public Object copyResult(Object var1, ORB var2) throws RemoteException;

    public Object readResult(InputStream var1);

    public void writeResult(OutputStream var1, Object var2);

    public boolean isDeclaredException(Throwable var1);

    public void writeException(OutputStream var1, Exception var2);

    public Exception readException(ApplicationException var1);
}

