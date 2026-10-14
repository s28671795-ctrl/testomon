/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.NullServantImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorBase_R;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;

public class POAPolicyMediatorImpl_R_AOM
extends POAPolicyMediatorBase_R {
    POAPolicyMediatorImpl_R_AOM(Policies policies, POAImpl poa) {
        super(policies, poa);
        if (!policies.useActiveMapOnly()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
    }

    @Override
    protected Object internalGetServant(byte[] id, String operation) throws ForwardRequest {
        Object servant = this.internalIdToServant(id);
        if (servant == null) {
            servant = new NullServantImpl(this.poa.invocationWrapper().nullServant());
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
        throw new WrongPolicy();
    }

    @Override
    public void setDefaultServant(Servant servant) throws WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    public Servant idToServant(byte[] id) throws WrongPolicy, ObjectNotActive {
        Servant s2 = this.internalIdToServant(id);
        if (s2 == null) {
            throw new ObjectNotActive();
        }
        return s2;
    }
}

