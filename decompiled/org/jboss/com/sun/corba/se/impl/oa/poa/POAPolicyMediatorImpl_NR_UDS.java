/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorBase;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;

public class POAPolicyMediatorImpl_NR_UDS
extends POAPolicyMediatorBase {
    private Servant defaultServant;

    POAPolicyMediatorImpl_NR_UDS(Policies policies, POAImpl poa) {
        super(policies, poa);
        if (policies.retainServants()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
        if (!policies.useDefaultServant()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
        this.defaultServant = null;
    }

    @Override
    protected Object internalGetServant(byte[] id, String operation) throws ForwardRequest {
        if (this.defaultServant == null) {
            throw this.poa.invocationWrapper().poaNoDefaultServant();
        }
        return this.defaultServant;
    }

    @Override
    public void returnServant() {
    }

    @Override
    public void etherealizeAll() {
    }

    @Override
    public void clearAOM() {
    }

    @Override
    public ServantManager getServantManager() throws WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    public void setServantManager(ServantManager servantManager) throws WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    public Servant getDefaultServant() throws NoServant, WrongPolicy {
        if (this.defaultServant == null) {
            throw new NoServant();
        }
        return this.defaultServant;
    }

    @Override
    public void setDefaultServant(Servant servant) throws WrongPolicy {
        this.defaultServant = servant;
        this.setDelegate(this.defaultServant, "DefaultServant".getBytes());
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
        if (this.defaultServant != null) {
            return this.defaultServant;
        }
        throw new ObjectNotActive();
    }
}

