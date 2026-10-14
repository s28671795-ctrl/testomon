/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import javax.rmi.CORBA.Stub;
import org.omg.CORBA.ORB;

public interface StubDelegate {
    public int hashCode(Stub var1);

    public boolean equals(Stub var1, Object var2);

    public String toString(Stub var1);

    public void connect(Stub var1, ORB var2) throws RemoteException;

    public void readObject(Stub var1, ObjectInputStream var2) throws IOException, ClassNotFoundException;

    public void writeObject(Stub var1, ObjectOutputStream var2) throws IOException;
}

