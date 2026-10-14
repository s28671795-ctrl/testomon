/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.NullServantImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorBase;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.jboss.com.sun.corba.se.spi.oa.OAInvocationInfo;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantLocator;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
import org.omg.PortableServer.ServantManager;

public class POAPolicyMediatorImpl_NR_USM
extends POAPolicyMediatorBase {
    private ServantLocator locator;

    POAPolicyMediatorImpl_NR_USM(Policies policies, POAImpl poa) {
        super(policies, poa);
        if (policies.retainServants()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
        if (!policies.useServantManager()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
        this.locator = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object internalGetServant(byte[] id, String operation) throws ForwardRequest {
        Object servant;
        if (this.locator == null) {
            throw this.poa.invocationWrapper().poaNoServantManager();
        }
        CookieHolder cookieHolder = this.orb.peekInvocationInfo().getCookieHolder();
        try {
            this.poa.unlock();
            servant = this.locator.preinvoke(id, this.poa, operation, cookieHolder);
            if (servant == null) {
                servant = new NullServantImpl(this.poa.omgInvocationWrapper().nullServantReturned());
            } else {
                this.setDelegate((Servant)servant, id);
            }
        }
        finally {
            this.poa.lock();
        }
        return servant;
    }

    @Override
    public void returnServant() {
        OAInvocationInfo info = this.orb.peekInvocationInfo();
        if (this.locator == null) {
            return;
        }
        try {
            this.poa.unlock();
            this.locator.postinvoke(info.id(), (POA)((Object)info.oa()), info.getOperation(), info.getCookieHolder().value, (Servant)info.getServantContainer());
        }
        catch (ForwardRequest e) {
            e.printStackTrace();
        }
        finally {
            this.poa.lock();
        }
    }

    @Override
    public void etherealizeAll() {
    }

    @Override
    public void clearAOM() {
    }

    @Override
    public ServantManager getServantManager() throws WrongPolicy {
        return this.locator;
    }

    @Override
    public void setServantManager(ServantManager servantManager) throws WrongPolicy {
        if (this.locator != null) {
            throw this.poa.invocationWrapper().servantManagerAlreadySet();
        }
        if (!(servantManager instanceof ServantLocator)) {
            throw this.poa.invocationWrapper().servantManagerBadType();
        }
        this.locator = (ServantLocator)servantManager;
    }

    @Override
    public Servant getDefaultServant() throws NoServant, WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    public void setDefaultServant(Servant servant) throws WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    public final void activateObject(byte[] id, Servant servant) throws WrongPolicy, ServantAlreadyActive, ObjectAlreadyActive {
        throw new WrongPolicy();
    }

    @Override
    public Servant deactivateObject(byte[] id) throws ObjectNotActive, WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    public byte[] servantToId(Servant servant) throws ServantNotActive, WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    public Servant idToServant(byte[] id) throws WrongPolicy, ObjectNotActive {
        throw new WrongPolicy();
    }
}

