/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorBase_R;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;

public class POAPolicyMediatorImpl_R_UDS
extends POAPolicyMediatorBase_R {
    private Servant defaultServant = null;

    POAPolicyMediatorImpl_R_UDS(Policies policies, POAImpl poa) {
        super(policies, poa);
        if (!policies.useDefaultServant()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
    }

    @Override
    protected Object internalGetServant(byte[] id, String operation) throws ForwardRequest {
        Servant servant = this.internalIdToServant(id);
        if (servant == null) {
            servant = this.defaultServant;
        }
        if (servant == null) {
            throw this.poa.invocationWrapper().poaNoDefaultServant();
        }
        return servant;
    }

    @Override
    public void etherealizeAll() {
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
    public Servant idToServant(byte[] id) throws WrongPolicy, ObjectNotActive {
        ActiveObjectMap.Key key = new ActiveObjectMap.Key(id);
        Servant s2 = this.internalKeyToServant(key);
        if (s2 == null && this.defaultServant != null) {
            s2 = this.defaultServant;
        }
        if (s2 == null) {
            throw new ObjectNotActive();
        }
        return s2;
    }
}

