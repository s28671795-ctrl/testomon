/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.rmi.CORBA.Util;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.DynamicAccessPermission;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfoList;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.LinkedInvocationHandler;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaClientDelegate;
import org.jboss.com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
import org.jboss.com.sun.corba.se.spi.transport.CorbaContactInfoList;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class StubInvocationHandlerImpl
implements LinkedInvocationHandler {
    private transient PresentationManager.ClassData classData;
    private transient PresentationManager pm;
    private transient Object stub;
    private transient Proxy self;

    @Override
    public void setProxy(Proxy self) {
        this.self = self;
    }

    @Override
    public Proxy getProxy() {
        return this.self;
    }

    public StubInvocationHandlerImpl(PresentationManager pm, PresentationManager.ClassData classData, Object stub) {
        SecurityManager s2 = System.getSecurityManager();
        if (s2 != null) {
            s2.checkPermission(new DynamicAccessPermission("access"));
        }
        this.classData = classData;
        this.pm = pm;
        this.stub = stub;
    }

    private boolean isLocal() {
        CorbaClientDelegate cdel;
        ContactInfoList cil;
        boolean result = false;
        Delegate delegate = StubAdapter.getDelegate(this.stub);
        if (delegate instanceof CorbaClientDelegate && (cil = (cdel = (CorbaClientDelegate)delegate).getContactInfoList()) instanceof CorbaContactInfoList) {
            CorbaContactInfoList ccil = (CorbaContactInfoList)cil;
            LocalClientRequestDispatcher lcrd = ccil.getLocalClientRequestDispatcher();
            result = lcrd.useLocalInvocation(null);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public java.lang.Object invoke(java.lang.Object proxy, final Method method2, java.lang.Object[] args2) throws Throwable {
        String giopMethodName = this.classData.getIDLNameTranslator().getIDLName(method2);
        DynamicMethodMarshaller dmm = this.pm.getDynamicMethodMarshaller(method2);
        Delegate delegate = null;
        try {
            delegate = StubAdapter.getDelegate(this.stub);
        }
        catch (SystemException ex) {
            throw Util.mapSystemException(ex);
        }
        if (!this.isLocal()) {
            try {
                InputStream in = null;
                try {
                    OutputStream out = (OutputStream)delegate.request(this.stub, giopMethodName, true);
                    dmm.writeArguments(out, args2);
                    in = (InputStream)delegate.invoke(this.stub, out);
                    java.lang.Object object = dmm.readResult(in);
                    delegate.releaseReply(this.stub, in);
                    return object;
                }
                catch (ApplicationException ex) {
                    throw dmm.readException(ex);
                    catch (RemarshalException ex2) {
                        java.lang.Object object = this.invoke(proxy, method2, args2);
                        return object;
                    }
                }
                finally {
                    delegate.releaseReply(this.stub, in);
                }
            }
            catch (SystemException ex) {
                throw Util.mapSystemException(ex);
            }
        }
        ORB orb = (ORB)delegate.orb(this.stub);
        ServantObject so = delegate.servant_preinvoke(this.stub, giopMethodName, method2.getDeclaringClass());
        if (so == null) {
            return this.invoke(this.stub, method2, args2);
        }
        try {
            java.lang.Object[] copies = dmm.copyArguments(args2, orb);
            if (!method2.isAccessible()) {
                AccessController.doPrivileged(new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        method2.setAccessible(true);
                        return null;
                    }
                });
            }
            java.lang.Object result = method2.invoke(so.servant, copies);
            java.lang.Object object = dmm.copyResult(result, orb);
            return object;
        }
        catch (InvocationTargetException ex) {
            Throwable mex = ex.getCause();
            Throwable exCopy = (Throwable)Util.copyObject(mex, orb);
            if (!dmm.isDeclaredException(exCopy)) throw Util.wrapException(exCopy);
            throw exCopy;
        }
        catch (Throwable thr) {
            if (!(thr instanceof ThreadDeath)) throw Util.wrapException(thr);
            throw (ThreadDeath)thr;
        }
        finally {
            delegate.servant_postinvoke(this.stub, so);
        }
    }
}

