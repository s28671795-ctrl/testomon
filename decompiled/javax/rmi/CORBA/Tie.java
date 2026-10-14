/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.InvokeHandler;

public interface Tie
extends InvokeHandler {
    public Object thisObject();

    public void deactivate() throws NoSuchObjectException;

    public ORB orb();

    public void orb(ORB var1);

    public void setTarget(Remote var1);

    public Remote getTarget();
}

