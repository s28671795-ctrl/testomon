/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Object;

public class ForwardException
extends RuntimeException {
    private static final long serialVersionUID = 3312717244337667473L;
    private Object obj;
    private IOR ior;

    public ForwardException(ORB orb, IOR ior) {
        this.obj = null;
        this.ior = ior;
    }

    public ForwardException(ORB orb, Object obj) {
        if (obj instanceof LocalObject) {
            throw new BAD_PARAM();
        }
        this.obj = obj;
        this.ior = null;
    }

    public synchronized Object getObject() {
        if (this.obj == null) {
            this.obj = ORBUtility.makeObjectReference(this.ior);
        }
        return this.obj;
    }

    public synchronized IOR getIOR() {
        if (this.ior == null) {
            this.ior = ORBUtility.getIOR(this.obj);
        }
        return this.ior;
    }
}

