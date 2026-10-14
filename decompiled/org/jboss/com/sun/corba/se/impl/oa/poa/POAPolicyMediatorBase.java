/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.poa.DelegateImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAFactory;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAManagerImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediator;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBConstants;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;

public abstract class POAPolicyMediatorBase
implements POAPolicyMediator {
    protected POAImpl poa;
    protected ORB orb;
    private int sysIdCounter;
    private Policies policies;
    private DelegateImpl delegateImpl;
    private int serverid;
    private int scid;
    protected boolean isImplicit;
    protected boolean isUnique;
    protected boolean isSystemId;

    @Override
    public final Policies getPolicies() {
        return this.policies;
    }

    @Override
    public final int getScid() {
        return this.scid;
    }

    @Override
    public final int getServerId() {
        return this.serverid;
    }

    POAPolicyMediatorBase(Policies policies, POAImpl poa) {
        if (policies.isSingleThreaded()) {
            throw poa.invocationWrapper().singleThreadNotSupported();
        }
        POAManagerImpl poam = (POAManagerImpl)poa.the_POAManager();
        POAFactory poaf = poam.getFactory();
        this.delegateImpl = (DelegateImpl)poaf.getDelegateImpl();
        this.policies = policies;
        this.poa = poa;
        this.orb = poa.getORB();
        switch (policies.servantCachingLevel()) {
            case 0: {
                this.scid = 32;
                break;
            }
            case 1: {
                this.scid = 36;
                break;
            }
            case 2: {
                this.scid = 40;
                break;
            }
            case 3: {
                this.scid = 44;
            }
        }
        if (policies.isTransient()) {
            this.serverid = this.orb.getTransientServerId();
        } else {
            this.serverid = this.orb.getORBData().getPersistentServerId();
            this.scid = ORBConstants.makePersistent(this.scid);
        }
        this.isImplicit = policies.isImplicitlyActivated();
        this.isUnique = policies.isUniqueIds();
        this.isSystemId = policies.isSystemAssignedIds();
        this.sysIdCounter = 0;
    }

    @Override
    public final Object getInvocationServant(byte[] id, String operation) throws ForwardRequest {
        Object result = this.internalGetServant(id, operation);
        return result;
    }

    protected final void setDelegate(Servant servant, byte[] id) {
        servant._set_delegate(this.delegateImpl);
    }

    @Override
    public synchronized byte[] newSystemId() throws WrongPolicy {
        if (!this.isSystemId) {
            throw new WrongPolicy();
        }
        byte[] array = new byte[8];
        ORBUtility.intToBytes(++this.sysIdCounter, array, 0);
        ORBUtility.intToBytes(this.poa.getPOAId(), array, 4);
        return array;
    }

    protected abstract Object internalGetServant(byte[] var1, String var2) throws ForwardRequest;
}

