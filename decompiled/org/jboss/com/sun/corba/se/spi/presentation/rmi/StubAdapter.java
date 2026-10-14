/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.presentation.rmi;

import java.rmi.RemoteException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Tie;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAManagerImpl;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicStub;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;

public abstract class StubAdapter {
    private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");

    private StubAdapter() {
    }

    public static boolean isStubClass(Class<?> cls) {
        return ObjectImpl.class.isAssignableFrom(cls) || DynamicStub.class.isAssignableFrom(cls);
    }

    public static boolean isStub(java.lang.Object stub) {
        return stub instanceof DynamicStub || stub instanceof ObjectImpl;
    }

    public static void setDelegate(java.lang.Object stub, Delegate delegate) {
        if (stub instanceof DynamicStub) {
            ((DynamicStub)stub).setDelegate(delegate);
        } else if (stub instanceof ObjectImpl) {
            ((ObjectImpl)stub)._set_delegate(delegate);
        } else {
            throw wrapper.setDelegateRequiresStub();
        }
    }

    public static Object activateServant(Servant servant) {
        POA poa = servant._default_POA();
        Object ref = null;
        try {
            ref = poa.servant_to_reference(servant);
        }
        catch (ServantNotActive sna) {
            throw wrapper.getDelegateServantNotActive(sna);
        }
        catch (WrongPolicy wp) {
            throw wrapper.getDelegateWrongPolicy(wp);
        }
        POAManager mgr = poa.the_POAManager();
        if (mgr instanceof POAManagerImpl) {
            POAManagerImpl mgrImpl = (POAManagerImpl)mgr;
            mgrImpl.implicitActivation();
        }
        return ref;
    }

    public static Object activateTie(Tie tie) {
        if (tie instanceof ObjectImpl) {
            return tie.thisObject();
        }
        if (tie instanceof Servant) {
            Servant servant = (Servant)((java.lang.Object)tie);
            return StubAdapter.activateServant(servant);
        }
        throw wrapper.badActivateTieCall();
    }

    public static Delegate getDelegate(java.lang.Object stub) {
        if (stub instanceof DynamicStub) {
            return ((DynamicStub)stub).getDelegate();
        }
        if (stub instanceof ObjectImpl) {
            return ((ObjectImpl)stub)._get_delegate();
        }
        if (stub instanceof Tie) {
            Tie tie = (Tie)stub;
            Object ref = StubAdapter.activateTie(tie);
            return StubAdapter.getDelegate(ref);
        }
        throw wrapper.getDelegateRequiresStub();
    }

    public static ORB getORB(java.lang.Object stub) {
        if (stub instanceof DynamicStub) {
            return ((DynamicStub)stub).getORB();
        }
        if (stub instanceof ObjectImpl) {
            return ((ObjectImpl)stub)._orb();
        }
        throw wrapper.getOrbRequiresStub();
    }

    public static String[] getTypeIds(java.lang.Object stub) {
        if (stub instanceof DynamicStub) {
            return ((DynamicStub)stub).getTypeIds();
        }
        if (stub instanceof ObjectImpl) {
            return ((ObjectImpl)stub)._ids();
        }
        throw wrapper.getTypeIdsRequiresStub();
    }

    public static void connect(java.lang.Object stub, ORB orb) throws RemoteException {
        if (stub instanceof DynamicStub) {
            ((DynamicStub)stub).connect(orb);
        } else if (stub instanceof Stub) {
            ((Stub)stub).connect(orb);
        } else if (stub instanceof ObjectImpl) {
            orb.connect((Object)stub);
        } else {
            throw wrapper.connectRequiresStub();
        }
    }

    public static boolean isLocal(java.lang.Object stub) {
        if (stub instanceof DynamicStub) {
            return ((DynamicStub)stub).isLocal();
        }
        if (stub instanceof ObjectImpl) {
            return ((ObjectImpl)stub)._is_local();
        }
        throw wrapper.isLocalRequiresStub();
    }

    public static OutputStream request(java.lang.Object stub, String operation, boolean responseExpected) {
        if (stub instanceof DynamicStub) {
            return ((DynamicStub)stub).request(operation, responseExpected);
        }
        if (stub instanceof ObjectImpl) {
            return ((ObjectImpl)stub)._request(operation, responseExpected);
        }
        throw wrapper.requestRequiresStub();
    }
}

