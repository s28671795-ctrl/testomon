/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.servicecontext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContext;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA_2_3.portable.InputStream;

public class ServiceContextData {
    private Class<?> scClass;
    private Constructor<?> scConstructor;
    private int scId;

    private void dprint(String msg) {
        ORBUtility.dprint(this, msg);
    }

    private void throwBadParam(String msg, Throwable exc) {
        BAD_PARAM error = new BAD_PARAM(msg);
        if (exc != null) {
            error.initCause(exc);
        }
        throw error;
    }

    public ServiceContextData(Class<?> cls) {
        block19: {
            if (ORB.ORBInitDebug) {
                this.dprint("ServiceContextData constructor called for class " + cls);
            }
            this.scClass = cls;
            try {
                int mod;
                if (ORB.ORBInitDebug) {
                    this.dprint("Finding constructor for " + cls);
                }
                Class[] args2 = new Class[]{InputStream.class, GIOPVersion.class};
                try {
                    this.scConstructor = cls.getConstructor(args2);
                }
                catch (NoSuchMethodException nsme) {
                    this.throwBadParam("Class does not have an InputStream constructor", nsme);
                }
                if (ORB.ORBInitDebug) {
                    this.dprint("Finding SERVICE_CONTEXT_ID field in " + cls);
                }
                Field fld = null;
                try {
                    fld = cls.getField("SERVICE_CONTEXT_ID");
                }
                catch (NoSuchFieldException nsfe) {
                    this.throwBadParam("Class does not have a SERVICE_CONTEXT_ID member", nsfe);
                }
                catch (SecurityException se) {
                    this.throwBadParam("Could not access SERVICE_CONTEXT_ID member", se);
                }
                if (ORB.ORBInitDebug) {
                    this.dprint("Checking modifiers of SERVICE_CONTEXT_ID field in " + cls);
                }
                if (!(Modifier.isPublic(mod = fld.getModifiers()) && Modifier.isStatic(mod) && Modifier.isFinal(mod))) {
                    this.throwBadParam("SERVICE_CONTEXT_ID field is not public static final", null);
                }
                if (ORB.ORBInitDebug) {
                    this.dprint("Getting value of SERVICE_CONTEXT_ID in " + cls);
                }
                try {
                    this.scId = fld.getInt(null);
                }
                catch (IllegalArgumentException iae) {
                    this.throwBadParam("SERVICE_CONTEXT_ID not convertible to int", iae);
                }
                catch (IllegalAccessException iae2) {
                    this.throwBadParam("Could not access value of SERVICE_CONTEXT_ID", iae2);
                }
            }
            catch (BAD_PARAM nssc) {
                if (ORB.ORBInitDebug) {
                    this.dprint("Exception in ServiceContextData constructor: " + nssc);
                }
                throw nssc;
            }
            catch (Throwable thr) {
                if (!ORB.ORBInitDebug) break block19;
                this.dprint("Unexpected Exception in ServiceContextData constructor: " + thr);
            }
        }
        if (ORB.ORBInitDebug) {
            this.dprint("ServiceContextData constructor completed");
        }
    }

    public ServiceContext makeServiceContext(InputStream is, GIOPVersion gv) {
        Object[] args2 = new Object[]{is, gv};
        ServiceContext sc = null;
        try {
            sc = (ServiceContext)this.scConstructor.newInstance(args2);
        }
        catch (IllegalArgumentException iae) {
            this.throwBadParam("InputStream constructor argument error", iae);
        }
        catch (IllegalAccessException iae2) {
            this.throwBadParam("InputStream constructor argument error", iae2);
        }
        catch (InstantiationException ie) {
            this.throwBadParam("InputStream constructor called for abstract class", ie);
        }
        catch (InvocationTargetException ite) {
            this.throwBadParam("InputStream constructor threw exception " + ite.getTargetException(), ite);
        }
        return sc;
    }

    int getId() {
        return this.scId;
    }

    public String toString() {
        return "ServiceContextData[ scClass=" + this.scClass + " scConstructor=" + this.scConstructor + " scId=" + this.scId + " ]";
    }
}

