/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import javax.rmi.CORBA.Tie;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.DynamicAccessPermission;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CORBA.portable.UnknownException;
import org.omg.CORBA_2_3.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;

public final class ReflectiveTie
extends Servant
implements Tie {
    private Remote target = null;
    private PresentationManager pm;
    private PresentationManager.ClassData classData = null;
    private ORBUtilSystemException wrapper = null;

    public ReflectiveTie(PresentationManager pm, ORBUtilSystemException wrapper) {
        SecurityManager s2 = System.getSecurityManager();
        if (s2 != null) {
            s2.checkPermission(new DynamicAccessPermission("access"));
        }
        this.pm = pm;
        this.wrapper = wrapper;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] objectId) {
        return this.classData.getTypeIds();
    }

    @Override
    public void setTarget(Remote target) {
        this.target = target;
        if (target == null) {
            this.classData = null;
        } else {
            Class<?> targetClass = target.getClass();
            this.classData = this.pm.getClassData(targetClass);
        }
    }

    @Override
    public Remote getTarget() {
        return this.target;
    }

    @Override
    public Object thisObject() {
        return this._this_object();
    }

    @Override
    public void deactivate() {
        try {
            this._poa().deactivate_object(this._poa().servant_to_id(this));
        }
        catch (WrongPolicy wrongPolicy) {
        }
        catch (ObjectNotActive objectNotActive) {
        }
        catch (ServantNotActive servantNotActive) {
            // empty catch block
        }
    }

    @Override
    public org.omg.CORBA.ORB orb() {
        return this._orb();
    }

    @Override
    public void orb(org.omg.CORBA.ORB orb) {
        try {
            ((ORB)orb).set_delegate(this);
        }
        catch (ClassCastException e) {
            throw this.wrapper.badOrbForServant(e);
        }
    }

    @Override
    public org.omg.CORBA.portable.OutputStream _invoke(String method2, org.omg.CORBA.portable.InputStream _in, ResponseHandler reply) {
        Method javaMethod = null;
        DynamicMethodMarshaller dmm = null;
        try {
            InputStream in = (InputStream)_in;
            javaMethod = this.classData.getIDLNameTranslator().getMethod(method2);
            if (javaMethod == null) {
                throw this.wrapper.methodNotFoundInTie(method2, this.target.getClass().getName());
            }
            dmm = this.pm.getDynamicMethodMarshaller(javaMethod);
            java.lang.Object[] args2 = dmm.readArguments(in);
            java.lang.Object result = javaMethod.invoke((java.lang.Object)this.target, args2);
            OutputStream os = (OutputStream)reply.createReply();
            dmm.writeResult(os, result);
            return os;
        }
        catch (IllegalAccessException ex) {
            throw this.wrapper.invocationErrorInReflectiveTie(ex, (java.lang.Object)javaMethod.getName(), (java.lang.Object)javaMethod.getDeclaringClass().getName());
        }
        catch (IllegalArgumentException ex) {
            throw this.wrapper.invocationErrorInReflectiveTie(ex, (java.lang.Object)javaMethod.getName(), (java.lang.Object)javaMethod.getDeclaringClass().getName());
        }
        catch (InvocationTargetException ex) {
            Throwable thr = ex.getCause();
            if (thr instanceof SystemException) {
                throw (SystemException)thr;
            }
            if (thr instanceof Exception && dmm.isDeclaredException(thr)) {
                OutputStream os = (OutputStream)reply.createExceptionReply();
                dmm.writeException(os, (Exception)thr);
                return os;
            }
            throw new UnknownException(thr);
        }
    }
}

