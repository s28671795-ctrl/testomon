/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.io;

import java.util.Hashtable;
import java.util.Stack;
import javax.rmi.CORBA.Util;
import javax.rmi.CORBA.ValueHandler;
import org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass;
import org.jboss.com.sun.corba.se.impl.io.ValueHandlerImpl;
import org.jboss.com.sun.corba.se.impl.io.ValueUtility;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.org.omg.CORBA.Repository;
import org.jboss.com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.jboss.com.sun.org.omg.SendingContext._CodeBaseImplBase;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.ORB;

public class FVDCodeBaseImpl
extends _CodeBaseImplBase {
    private static final long serialVersionUID = 5813777342105483423L;
    private static Hashtable<String, FullValueDescription> fvds = new Hashtable();
    private transient ORB orb = null;
    private transient OMGSystemException wrapper = OMGSystemException.get("rpc.encoding");
    private transient ValueHandlerImpl vhandler = null;

    void setValueHandler(ValueHandler vh) {
        this.vhandler = (ValueHandlerImpl)vh;
    }

    @Override
    public Repository get_ir() {
        return null;
    }

    @Override
    public String implementation(String x) {
        try {
            String result;
            if (this.vhandler == null) {
                this.vhandler = new ValueHandlerImpl(false);
            }
            if ((result = Util.getCodebase(this.vhandler.getClassFromType(x))) == null) {
                return "";
            }
            return result;
        }
        catch (ClassNotFoundException cnfe) {
            throw this.wrapper.missingLocalValueImpl(CompletionStatus.COMPLETED_MAYBE, cnfe);
        }
    }

    @Override
    public String[] implementations(String[] x) {
        String[] result = new String[x.length];
        for (int i = 0; i < x.length; ++i) {
            result[i] = this.implementation(x[i]);
        }
        return result;
    }

    @Override
    public FullValueDescription meta(String x) {
        try {
            FullValueDescription result = fvds.get(x);
            if (result == null) {
                if (this.vhandler == null) {
                    this.vhandler = new ValueHandlerImpl(false);
                }
                try {
                    result = ValueUtility.translate(this._orb(), ObjectStreamClass.lookup(this.vhandler.getAnyClassFromType(x)), this.vhandler);
                }
                catch (Throwable t2) {
                    if (this.orb == null) {
                        this.orb = ORB.init();
                    }
                    result = ValueUtility.translate(this.orb, ObjectStreamClass.lookup(this.vhandler.getAnyClassFromType(x)), this.vhandler);
                }
                if (result != null) {
                    fvds.put(x, result);
                } else {
                    throw this.wrapper.missingLocalValueImpl(CompletionStatus.COMPLETED_MAYBE);
                }
            }
            return result;
        }
        catch (Throwable t3) {
            throw this.wrapper.incompatibleValueImpl(CompletionStatus.COMPLETED_MAYBE, t3);
        }
    }

    @Override
    public FullValueDescription[] metas(String[] x) {
        FullValueDescription[] descriptions = new FullValueDescription[x.length];
        for (int i = 0; i < x.length; ++i) {
            descriptions[i] = this.meta(x[i]);
        }
        return descriptions;
    }

    @Override
    public String[] bases(String x) {
        try {
            if (this.vhandler == null) {
                this.vhandler = new ValueHandlerImpl(false);
            }
            Stack<String> repIds = new Stack<String>();
            Class<?> parent = ObjectStreamClass.lookup(this.vhandler.getClassFromType(x)).forClass().getSuperclass();
            while (!parent.equals(Object.class)) {
                repIds.push(this.vhandler.createForAnyType(parent));
                parent = parent.getSuperclass();
            }
            String[] result = new String[repIds.size()];
            for (int i = result.length - 1; i >= 0; ++i) {
                result[i] = (String)repIds.pop();
            }
            return result;
        }
        catch (Throwable t2) {
            throw this.wrapper.missingLocalValueImpl(CompletionStatus.COMPLETED_MAYBE, t2);
        }
    }
}

