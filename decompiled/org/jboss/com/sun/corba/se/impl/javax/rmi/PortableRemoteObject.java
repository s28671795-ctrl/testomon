/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.javax.rmi;

import java.io.Externalizable;
import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteStub;
import java.rmi.server.UnicastRemoteObject;
import javax.rmi.CORBA.PortableRemoteObjectDelegate;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;

public class PortableRemoteObject
implements PortableRemoteObjectDelegate {
    @Override
    public void exportObject(Remote obj) throws RemoteException {
        if (obj == null) {
            throw new NullPointerException("invalid argument");
        }
        if (Util.getTie(obj) != null) {
            throw new ExportException(obj.getClass().getName() + " already exported");
        }
        Tie theTie = Utility.loadTie(obj);
        if (theTie != null) {
            Util.registerTarget(theTie, obj);
        } else {
            UnicastRemoteObject.exportObject(obj);
        }
    }

    @Override
    public Remote toStub(Remote obj) throws NoSuchObjectException {
        Remote result = null;
        if (obj == null) {
            throw new NullPointerException("invalid argument");
        }
        if (StubAdapter.isStub(obj)) {
            return obj;
        }
        if (obj instanceof RemoteStub) {
            return obj;
        }
        Tie theTie = Util.getTie(obj);
        if (theTie != null) {
            result = Utility.loadStub(theTie, null, null, true);
        } else if (Utility.loadTie(obj) == null) {
            result = RemoteObject.toStub(obj);
        }
        if (result == null) {
            throw new NoSuchObjectException("object not exported");
        }
        return result;
    }

    @Override
    public void unexportObject(Remote obj) throws NoSuchObjectException {
        if (obj == null) {
            throw new NullPointerException("invalid argument");
        }
        if (StubAdapter.isStub(obj) || obj instanceof RemoteStub) {
            throw new NoSuchObjectException("Can only unexport a server object.");
        }
        Tie theTie = Util.getTie(obj);
        if (theTie != null) {
            Util.unexportObject(obj);
        } else if (Utility.loadTie(obj) == null) {
            UnicastRemoteObject.unexportObject(obj, true);
        } else {
            throw new NoSuchObjectException("Object not exported.");
        }
    }

    @Override
    public java.lang.Object narrow(java.lang.Object narrowFrom, Class<?> narrowTo) throws ClassCastException {
        if (narrowFrom == null) {
            return null;
        }
        if (narrowTo == null) {
            throw new NullPointerException("invalid argument");
        }
        try {
            if (narrowTo.isAssignableFrom(narrowFrom.getClass())) {
                return narrowFrom;
            }
            if (narrowTo.isInterface() && narrowTo != Serializable.class && narrowTo != Externalizable.class) {
                Object narrowObj = (Object)narrowFrom;
                String id = RepositoryId.createForAnyType(narrowTo);
                if (narrowObj._is_a(id)) {
                    return Utility.loadStub(narrowObj, narrowTo);
                }
                throw new ClassCastException("Object is not of remote type " + narrowTo.getName());
            }
            throw new ClassCastException("Class " + narrowTo.getName() + " is not a valid remote interface");
        }
        catch (RuntimeException exception) {
            throw exception;
        }
        catch (Exception error) {
            ClassCastException cce = new ClassCastException();
            cce.initCause(error);
            throw cce;
        }
    }

    @Override
    public void connect(Remote target, Remote source2) throws RemoteException {
        if (target == null || source2 == null) {
            throw new NullPointerException("invalid argument");
        }
        ORB orb = null;
        try {
            if (StubAdapter.isStub(source2)) {
                orb = StubAdapter.getORB(source2);
            } else {
                Tie tie = Util.getTie(source2);
                if (tie != null) {
                    orb = tie.orb();
                }
            }
        }
        catch (SystemException e) {
            throw new RemoteException("'source' object not connected", e);
        }
        boolean targetIsIIOP = false;
        Tie targetTie = null;
        if (StubAdapter.isStub(target)) {
            targetIsIIOP = true;
        } else {
            targetTie = Util.getTie(target);
            if (targetTie != null) {
                targetIsIIOP = true;
            }
        }
        if (!targetIsIIOP) {
            if (orb != null) {
                throw new RemoteException("'source' object exported to IIOP, 'target' is JRMP");
            }
        } else {
            if (orb == null) {
                throw new RemoteException("'source' object is JRMP, 'target' is IIOP");
            }
            try {
                if (targetTie != null) {
                    try {
                        ORB existingOrb = targetTie.orb();
                        if (existingOrb == orb) {
                            return;
                        }
                        throw new RemoteException("'target' object was already connected");
                    }
                    catch (SystemException existingOrb) {
                        targetTie.orb(orb);
                    }
                } else {
                    StubAdapter.connect(target, orb);
                }
            }
            catch (SystemException e) {
                throw new RemoteException("'target' object was already connected", e);
            }
        }
    }
}

