/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.EmptyStackException;
import org.jboss.com.sun.corba.se.impl.logging.POASystemException;
import org.jboss.com.sun.corba.se.spi.oa.OAInvocationInfo;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapter;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.PortableServer.Current;
import org.omg.PortableServer.CurrentPackage.NoContext;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;

public class POACurrent
extends ObjectImpl
implements Current {
    private static final long serialVersionUID = 6652432719945390553L;
    private ORB orb;
    private POASystemException wrapper;

    public POACurrent(ORB orb) {
        this.orb = orb;
        this.wrapper = POASystemException.get(orb, "oa.invocation");
    }

    @Override
    public String[] _ids() {
        String[] ids = new String[]{"IDL:omg.org/PortableServer/Current:1.0"};
        return ids;
    }

    @Override
    public POA get_POA() throws NoContext {
        POA poa = (POA)((java.lang.Object)this.peekThrowNoContext().oa());
        this.throwNoContextIfNull(poa);
        return poa;
    }

    @Override
    public byte[] get_object_id() throws NoContext {
        byte[] objectid = this.peekThrowNoContext().id();
        this.throwNoContextIfNull(objectid);
        return objectid;
    }

    public ObjectAdapter getOA() {
        ObjectAdapter oa = this.peekThrowInternal().oa();
        this.throwInternalIfNull(oa);
        return oa;
    }

    public byte[] getObjectId() {
        byte[] objectid = this.peekThrowInternal().id();
        this.throwInternalIfNull(objectid);
        return objectid;
    }

    Servant getServant() {
        Servant servant = (Servant)this.peekThrowInternal().getServantContainer();
        return servant;
    }

    CookieHolder getCookieHolder() {
        CookieHolder cookieHolder = this.peekThrowInternal().getCookieHolder();
        this.throwInternalIfNull(cookieHolder);
        return cookieHolder;
    }

    public String getOperation() {
        String operation = this.peekThrowInternal().getOperation();
        this.throwInternalIfNull(operation);
        return operation;
    }

    void setServant(Servant servant) {
        this.peekThrowInternal().setServant(servant);
    }

    private OAInvocationInfo peekThrowNoContext() throws NoContext {
        OAInvocationInfo invocationInfo = null;
        try {
            invocationInfo = this.orb.peekInvocationInfo();
        }
        catch (EmptyStackException e) {
            throw new NoContext();
        }
        return invocationInfo;
    }

    private OAInvocationInfo peekThrowInternal() {
        OAInvocationInfo invocationInfo = null;
        try {
            invocationInfo = this.orb.peekInvocationInfo();
        }
        catch (EmptyStackException e) {
            throw this.wrapper.poacurrentUnbalancedStack(e);
        }
        return invocationInfo;
    }

    private void throwNoContextIfNull(java.lang.Object o) throws NoContext {
        if (o == null) {
            throw new NoContext();
        }
    }

    private void throwInternalIfNull(java.lang.Object o) {
        if (o == null) {
            throw this.wrapper.poacurrentNullField(CompletionStatus.COMPLETED_MAYBE);
        }
    }

    @Override
    public Object get_reference() throws NoContext {
        Servant servant = this.get_servant();
        if (servant != null) {
            return servant._this_object(this.orb);
        }
        return null;
    }

    @Override
    public Servant get_servant() throws NoContext {
        return this.getServant();
    }
}

