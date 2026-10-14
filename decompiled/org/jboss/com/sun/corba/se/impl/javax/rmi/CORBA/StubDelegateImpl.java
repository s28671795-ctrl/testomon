/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.javax.rmi.CORBA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.StubDelegate;
import org.jboss.com.sun.corba.se.impl.ior.StubIORImpl;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubConnectImpl;
import org.omg.CORBA.ORB;

public class StubDelegateImpl
implements StubDelegate {
    static UtilSystemException wrapper = UtilSystemException.get("rmiiiop");
    private StubIORImpl ior = null;

    public StubIORImpl getIOR() {
        return this.ior;
    }

    private void init(Stub self) {
        if (this.ior == null) {
            this.ior = new StubIORImpl(self);
        }
    }

    @Override
    public int hashCode(Stub self) {
        this.init(self);
        return this.ior.hashCode();
    }

    @Override
    public boolean equals(Stub self, Object obj) {
        if (self == obj) {
            return true;
        }
        if (!(obj instanceof Stub)) {
            return false;
        }
        Stub other = (Stub)obj;
        if (other.hashCode() != self.hashCode()) {
            return false;
        }
        return self.toString().equals(other.toString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StubDelegateImpl)) {
            return false;
        }
        StubDelegateImpl other = (StubDelegateImpl)obj;
        if (this.ior == null) {
            return this.ior == other.ior;
        }
        return this.ior.equals(other.ior);
    }

    @Override
    public String toString(Stub self) {
        if (this.ior == null) {
            return null;
        }
        return this.ior.toString();
    }

    @Override
    public void connect(Stub self, ORB orb) throws RemoteException {
        this.ior = StubConnectImpl.connect(this.ior, self, self, orb);
    }

    @Override
    public void readObject(Stub self, ObjectInputStream stream) throws IOException, ClassNotFoundException {
        if (this.ior == null) {
            this.ior = new StubIORImpl();
        }
        this.ior.doRead(stream);
    }

    @Override
    public void writeObject(Stub self, ObjectOutputStream stream) throws IOException {
        this.init(self);
        this.ior.doWrite(stream);
    }
}

