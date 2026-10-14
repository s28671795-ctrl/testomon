/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.rmi.RemoteException;
import javax.rmi.CORBA.Tie;
import org.jboss.com.sun.corba.se.impl.corba.CORBAObjectImpl;
import org.jboss.com.sun.corba.se.impl.ior.StubIORImpl;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ObjectImpl;

public abstract class StubConnectImpl {
    static UtilSystemException wrapper = UtilSystemException.get("rmiiiop");

    public static StubIORImpl connect(StubIORImpl ior, Object proxy, ObjectImpl stub, ORB orb) throws RemoteException {
        Delegate del = null;
        try {
            try {
                del = StubAdapter.getDelegate(stub);
                if (del.orb(stub) != orb) {
                    throw wrapper.connectWrongOrb();
                }
            }
            catch (BAD_OPERATION err) {
                if (ior == null) {
                    Tie tie = Utility.getAndForgetTie(proxy);
                    if (tie == null) {
                        throw wrapper.connectNoTie();
                    }
                    ORB existingOrb = orb;
                    try {
                        existingOrb = tie.orb();
                    }
                    catch (BAD_OPERATION exc) {
                        tie.orb(orb);
                    }
                    catch (BAD_INV_ORDER exc) {
                        tie.orb(orb);
                    }
                    if (existingOrb != orb) {
                        throw wrapper.connectTieWrongOrb();
                    }
                    del = StubAdapter.getDelegate(tie);
                    CORBAObjectImpl objref = new CORBAObjectImpl();
                    objref._set_delegate(del);
                    ior = new StubIORImpl(objref);
                } else {
                    del = ior.getDelegate(orb);
                }
                StubAdapter.setDelegate(stub, del);
            }
        }
        catch (SystemException exc) {
            throw new RemoteException("CORBA SystemException", exc);
        }
        return ior;
    }
}

