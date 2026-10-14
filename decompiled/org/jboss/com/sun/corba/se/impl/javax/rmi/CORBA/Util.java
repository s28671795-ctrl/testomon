/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.omg.CORBA.ACTIVITY_COMPLETED
 *  org.omg.CORBA.ACTIVITY_REQUIRED
 *  org.omg.CORBA.INVALID_ACTIVITY
 */
package org.jboss.com.sun.corba.se.impl.javax.rmi.CORBA;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.rmi.AccessException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.rmi.ServerException;
import java.rmi.UnexpectedException;
import java.rmi.server.RMIClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.EmptyStackException;
import java.util.Enumeration;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.UtilDelegate;
import javax.rmi.CORBA.ValueHandler;
import javax.transaction.InvalidTransactionException;
import javax.transaction.TransactionRequiredException;
import javax.transaction.TransactionRolledbackException;
import org.jboss.com.sun.corba.se.impl.corba.AnyImpl;
import org.jboss.com.sun.corba.se.impl.io.ValueHandlerImpl;
import org.jboss.com.sun.corba.se.impl.javax.rmi.CORBA.KeepAlive;
import org.jboss.com.sun.corba.se.impl.javax.rmi.RemoteObjectSubstitutionManager;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBClassLoader;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.util.IdentityHashtable;
import org.jboss.com.sun.corba.se.impl.util.JDKBridge;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfoList;
import org.jboss.com.sun.corba.se.spi.copyobject.CopierManager;
import org.jboss.com.sun.corba.se.spi.copyobject.ObjectCopier;
import org.jboss.com.sun.corba.se.spi.copyobject.ReflectiveCopyException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaClientDelegate;
import org.jboss.com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
import org.jboss.com.sun.corba.se.spi.transport.CorbaContactInfoList;
import org.omg.CORBA.ACTIVITY_COMPLETED;
import org.omg.CORBA.ACTIVITY_REQUIRED;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.INVALID_ACTIVITY;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.UnknownException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class Util
implements UtilDelegate {
    private static KeepAlive keepAlive = null;
    private static IdentityHashtable exportedServants = new IdentityHashtable();
    private static ValueHandlerImpl valueHandlerSingleton = new ValueHandlerImpl();
    private UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");
    public static Util instance = null;

    public Util() {
        instance = this;
    }

    public void unregisterTargetsForORB(org.omg.CORBA.ORB orb) {
        Enumeration<java.lang.Object> e = exportedServants.keys();
        while (e.hasMoreElements()) {
            java.lang.Object key = e.nextElement();
            Remote target = (Remote)(key instanceof Tie ? ((Tie)key).getTarget() : key);
            try {
                if (orb != this.getTie(target).orb()) continue;
                try {
                    this.unexportObject(target);
                }
                catch (NoSuchObjectException noSuchObjectException) {
                }
            }
            catch (BAD_OPERATION bAD_OPERATION) {}
        }
    }

    @Override
    public RemoteException mapSystemException(SystemException ex) {
        String status;
        if (ex instanceof UnknownException) {
            Throwable orig = ((UnknownException)ex).originalEx;
            if (orig instanceof Error) {
                return new ServerError("Error occurred in server thread", (Error)orig);
            }
            if (orig instanceof RemoteException) {
                return new ServerException("RemoteException occurred in server thread", (Exception)orig);
            }
            if (orig instanceof RuntimeException) {
                throw (RuntimeException)orig;
            }
        }
        String name = ex.getClass().getName();
        String corbaName = name.substring(name.lastIndexOf(46) + 1);
        switch (ex.completed.value()) {
            case 0: {
                status = "Yes";
                break;
            }
            case 1: {
                status = "No";
                break;
            }
            default: {
                status = "Maybe";
            }
        }
        String message = "CORBA " + corbaName + " " + ex.minor + " " + status;
        if (ex instanceof COMM_FAILURE) {
            return new MarshalException(message, ex);
        }
        if (ex instanceof INV_OBJREF) {
            NoSuchObjectException newEx = new NoSuchObjectException(message);
            newEx.detail = ex;
            return newEx;
        }
        if (ex instanceof NO_PERMISSION) {
            return new AccessException(message, ex);
        }
        if (ex instanceof MARSHAL) {
            return new MarshalException(message, ex);
        }
        if (ex instanceof OBJECT_NOT_EXIST) {
            NoSuchObjectException newEx = new NoSuchObjectException(message);
            newEx.detail = ex;
            return newEx;
        }
        if (ex instanceof TRANSACTION_REQUIRED) {
            TransactionRequiredException newEx = new TransactionRequiredException(message);
            newEx.detail = ex;
            return newEx;
        }
        if (ex instanceof TRANSACTION_ROLLEDBACK) {
            TransactionRolledbackException newEx = new TransactionRolledbackException(message);
            newEx.detail = ex;
            return newEx;
        }
        if (ex instanceof INVALID_TRANSACTION) {
            InvalidTransactionException newEx = new InvalidTransactionException(message);
            newEx.detail = ex;
            return newEx;
        }
        if (ex instanceof BAD_PARAM) {
            Exception inner = ex;
            if (ex.minor == 1398079489 || ex.minor == 1330446342) {
                inner = ex.getMessage() != null ? new NotSerializableException(ex.getMessage()) : new NotSerializableException();
                inner.initCause(ex);
            }
            return new MarshalException(message, inner);
        }
        if (ex instanceof ACTIVITY_REQUIRED) {
            try {
                Class<?> cl = ORBClassLoader.loadClass("javax.activity.ActivityRequiredException");
                Class[] params = new Class[]{String.class, Throwable.class};
                Constructor<?> cr = cl.getConstructor(params);
                java.lang.Object[] args2 = new java.lang.Object[]{message, ex};
                return (RemoteException)cr.newInstance(args2);
            }
            catch (Throwable e) {
                this.utilWrapper.classNotFound(e, (java.lang.Object)"javax.activity.ActivityRequiredException");
            }
        } else if (ex instanceof ACTIVITY_COMPLETED) {
            try {
                Class<?> cl = ORBClassLoader.loadClass("javax.activity.ActivityCompletedException");
                Class[] params = new Class[]{String.class, Throwable.class};
                Constructor<?> cr = cl.getConstructor(params);
                java.lang.Object[] args3 = new java.lang.Object[]{message, ex};
                return (RemoteException)cr.newInstance(args3);
            }
            catch (Throwable e) {
                this.utilWrapper.classNotFound(e, (java.lang.Object)"javax.activity.ActivityCompletedException");
            }
        } else if (ex instanceof INVALID_ACTIVITY) {
            try {
                Class<?> cl = ORBClassLoader.loadClass("javax.activity.InvalidActivityException");
                Class[] params = new Class[]{String.class, Throwable.class};
                Constructor<?> cr = cl.getConstructor(params);
                java.lang.Object[] args4 = new java.lang.Object[]{message, ex};
                return (RemoteException)cr.newInstance(args4);
            }
            catch (Throwable e) {
                this.utilWrapper.classNotFound(e, (java.lang.Object)"javax.activity.InvalidActivityException");
            }
        }
        return new RemoteException(message, ex);
    }

    @Override
    public void writeAny(org.omg.CORBA.portable.OutputStream out, java.lang.Object obj) {
        org.omg.CORBA.ORB orb = out.orb();
        Any any = orb.create_any();
        java.lang.Object newObj = Utility.autoConnect(obj, orb, false);
        if (newObj instanceof Object) {
            any.insert_Object((Object)newObj);
        } else if (newObj == null) {
            any.insert_Value(null, this.createTypeCodeForNull(orb));
        } else if (newObj instanceof Serializable) {
            TypeCode tc = this.createTypeCode((Serializable)newObj, any, orb);
            if (tc == null) {
                any.insert_Value((Serializable)newObj);
            } else {
                any.insert_Value((Serializable)newObj, tc);
            }
        } else if (newObj instanceof Remote) {
            ORBUtility.throwNotSerializableForCorba(newObj.getClass().getName());
        } else {
            ORBUtility.throwNotSerializableForCorba(newObj.getClass().getName());
        }
        out.write_any(any);
    }

    private TypeCode createTypeCode(Serializable obj, Any any, org.omg.CORBA.ORB orb) {
        if (any instanceof AnyImpl && orb instanceof ORB) {
            AnyImpl anyImpl = (AnyImpl)any;
            ORB ourORB = (ORB)orb;
            return anyImpl.createTypeCodeForClass(obj.getClass(), ourORB);
        }
        return null;
    }

    private TypeCode createTypeCodeForNull(org.omg.CORBA.ORB orb) {
        if (orb instanceof ORB) {
            ORB ourORB = (ORB)orb;
            if (!ORBVersionFactory.getFOREIGN().equals(ourORB.getORBVersion()) && ORBVersionFactory.getNEWER().compareTo(ourORB.getORBVersion()) > 0) {
                return orb.get_primitive_tc(TCKind.tk_value);
            }
        }
        String abstractBaseID = "IDL:omg.org/CORBA/AbstractBase:1.0";
        return orb.create_abstract_interface_tc(abstractBaseID, "");
    }

    @Override
    public java.lang.Object readAny(org.omg.CORBA.portable.InputStream in) {
        Any any = in.read_any();
        if (any.type().kind().value() == 14) {
            return any.extract_Object();
        }
        return any.extract_Value();
    }

    @Override
    public void writeRemoteObject(org.omg.CORBA.portable.OutputStream out, java.lang.Object obj) {
        java.lang.Object replacedObj = RemoteObjectSubstitutionManager.writeReplaceRemote(obj);
        java.lang.Object newObj = Utility.autoConnect(replacedObj, out.orb(), false);
        out.write_Object((Object)newObj);
    }

    @Override
    public void writeAbstractObject(org.omg.CORBA.portable.OutputStream out, java.lang.Object obj) {
        java.lang.Object replacedObj = RemoteObjectSubstitutionManager.writeReplaceRemote(obj);
        java.lang.Object newObj = Utility.autoConnect(replacedObj, out.orb(), false);
        ((OutputStream)out).write_abstract_interface(newObj);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void registerTarget(Tie tie, Remote target) {
        IdentityHashtable identityHashtable = exportedServants;
        synchronized (identityHashtable) {
            if (Util.lookupTie(target) == null) {
                exportedServants.put(target, tie);
                tie.setTarget(target);
                if (keepAlive == null) {
                    keepAlive = AccessController.doPrivileged(new PrivilegedAction<KeepAlive>(){

                        @Override
                        public KeepAlive run() {
                            return new KeepAlive();
                        }
                    });
                    keepAlive.start();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void unexportObject(Remote target) throws NoSuchObjectException {
        IdentityHashtable identityHashtable = exportedServants;
        synchronized (identityHashtable) {
            Tie cachedTie = Util.lookupTie(target);
            if (cachedTie != null) {
                exportedServants.remove(target);
                Utility.purgeStubForTie(cachedTie);
                Utility.purgeTieAndServant(cachedTie);
                try {
                    this.cleanUpTie(cachedTie);
                }
                catch (BAD_OPERATION bAD_OPERATION) {
                }
                catch (OBJ_ADAPTER oBJ_ADAPTER) {
                    // empty catch block
                }
                if (exportedServants.isEmpty()) {
                    keepAlive.quit();
                    keepAlive = null;
                }
            } else {
                throw new NoSuchObjectException("Tie not found");
            }
        }
    }

    protected void cleanUpTie(Tie cachedTie) throws NoSuchObjectException {
        cachedTie.setTarget(null);
        cachedTie.deactivate();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Tie getTie(Remote target) {
        IdentityHashtable identityHashtable = exportedServants;
        synchronized (identityHashtable) {
            return Util.lookupTie(target);
        }
    }

    private static Tie lookupTie(Remote target) {
        Tie result = (Tie)exportedServants.get(target);
        if (result == null && target instanceof Tie && exportedServants.contains(target)) {
            result = (Tie)((java.lang.Object)target);
        }
        return result;
    }

    @Override
    public ValueHandler createValueHandler() {
        return valueHandlerSingleton;
    }

    @Override
    public String getCodebase(Class<?> clz) {
        return RMIClassLoader.getClassAnnotation(clz);
    }

    @Override
    public Class<?> loadClass(String className, String remoteCodebase, ClassLoader loader) throws ClassNotFoundException {
        return JDKBridge.loadClass(className, remoteCodebase, loader);
    }

    @Override
    public boolean isLocal(Stub stub) throws RemoteException {
        boolean result = false;
        try {
            Delegate delegate = stub._get_delegate();
            if (delegate instanceof CorbaClientDelegate) {
                CorbaClientDelegate cdel = (CorbaClientDelegate)delegate;
                ContactInfoList cil = cdel.getContactInfoList();
                if (cil instanceof CorbaContactInfoList) {
                    CorbaContactInfoList ccil = (CorbaContactInfoList)cil;
                    LocalClientRequestDispatcher lcs = ccil.getLocalClientRequestDispatcher();
                    result = lcs.useLocalInvocation(null);
                }
            } else {
                result = delegate.is_local(stub);
            }
        }
        catch (SystemException e) {
            throw javax.rmi.CORBA.Util.mapSystemException(e);
        }
        return result;
    }

    @Override
    public RemoteException wrapException(Throwable orig) {
        if (orig instanceof SystemException) {
            return this.mapSystemException((SystemException)orig);
        }
        if (orig instanceof Error) {
            return new ServerError("Error occurred in server thread", (Error)orig);
        }
        if (orig instanceof RemoteException) {
            return new ServerException("RemoteException occurred in server thread", (Exception)orig);
        }
        if (orig instanceof RuntimeException) {
            throw (RuntimeException)orig;
        }
        if (orig instanceof Exception) {
            return new UnexpectedException(orig.toString(), (Exception)orig);
        }
        return new UnexpectedException(orig.toString());
    }

    @Override
    public java.lang.Object[] copyObjects(java.lang.Object[] obj, org.omg.CORBA.ORB orb) throws RemoteException {
        if (obj == null) {
            throw new NullPointerException();
        }
        Class<?> compType = obj.getClass().getComponentType();
        if (Remote.class.isAssignableFrom(compType) && !compType.isInterface()) {
            Remote[] result = new Remote[obj.length];
            System.arraycopy(obj, 0, result, 0, obj.length);
            return (java.lang.Object[])this.copyObject(result, orb);
        }
        return (java.lang.Object[])this.copyObject(obj, orb);
    }

    @Override
    public java.lang.Object copyObject(java.lang.Object obj, org.omg.CORBA.ORB orb) throws RemoteException {
        if (orb instanceof ORB) {
            ORB lorb = (ORB)orb;
            try {
                try {
                    return lorb.peekInvocationInfo().getCopierFactory().make().copy(obj);
                }
                catch (EmptyStackException exc) {
                    CopierManager cm = lorb.getCopierManager();
                    ObjectCopier copier = cm.getDefaultObjectCopierFactory().make();
                    return copier.copy(obj);
                }
            }
            catch (ReflectiveCopyException exc) {
                RemoteException rexc = new RemoteException();
                rexc.initCause(exc);
                throw rexc;
            }
        }
        OutputStream out = (OutputStream)orb.create_output_stream();
        out.write_value((Serializable)obj);
        InputStream in = (InputStream)out.create_input_stream();
        return in.read_value();
    }
}

