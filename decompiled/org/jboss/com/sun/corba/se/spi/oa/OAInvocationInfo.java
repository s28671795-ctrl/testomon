/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.oa;

import javax.rmi.CORBA.Tie;
import org.jboss.com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapter;
import org.omg.CORBA.portable.ServantObject;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;

public class OAInvocationInfo
extends ServantObject {
    private Object servantContainer;
    private ObjectAdapter oa;
    private byte[] oid;
    private CookieHolder cookieHolder;
    private String operation;
    private ObjectCopierFactory factory;

    public OAInvocationInfo(ObjectAdapter oa, byte[] id) {
        this.oa = oa;
        this.oid = id;
    }

    public OAInvocationInfo(OAInvocationInfo info, String operation) {
        this.servant = info.servant;
        this.servantContainer = info.servantContainer;
        this.cookieHolder = info.cookieHolder;
        this.oa = info.oa;
        this.oid = info.oid;
        this.factory = info.factory;
        this.operation = operation;
    }

    public ObjectAdapter oa() {
        return this.oa;
    }

    public byte[] id() {
        return this.oid;
    }

    public Object getServantContainer() {
        return this.servantContainer;
    }

    public CookieHolder getCookieHolder() {
        if (this.cookieHolder == null) {
            this.cookieHolder = new CookieHolder();
        }
        return this.cookieHolder;
    }

    public String getOperation() {
        return this.operation;
    }

    public ObjectCopierFactory getCopierFactory() {
        return this.factory;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setCopierFactory(ObjectCopierFactory factory2) {
        this.factory = factory2;
    }

    public void setServant(Object servant) {
        this.servantContainer = servant;
        this.servant = servant instanceof Tie ? ((Tie)servant).getTarget() : servant;
    }
}

