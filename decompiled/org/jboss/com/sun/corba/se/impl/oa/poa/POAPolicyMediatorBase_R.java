/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import org.jboss.com.sun.corba.se.impl.oa.poa.AOMEntry;
import org.jboss.com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAFactory;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAManagerImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorBase;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;

public abstract class POAPolicyMediatorBase_R
extends POAPolicyMediatorBase {
    protected ActiveObjectMap activeObjectMap;

    POAPolicyMediatorBase_R(Policies policies, POAImpl poa) {
        super(policies, poa);
        if (!policies.retainServants()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
        this.activeObjectMap = ActiveObjectMap.create(poa, !this.isUnique);
    }

    @Override
    public void returnServant() {
    }

    @Override
    public void clearAOM() {
        this.activeObjectMap.clear();
        this.activeObjectMap = null;
    }

    protected Servant internalKeyToServant(ActiveObjectMap.Key key) {
        AOMEntry entry = this.activeObjectMap.get(key);
        if (entry == null) {
            return null;
        }
        return this.activeObjectMap.getServant(entry);
    }

    protected Servant internalIdToServant(byte[] id) {
        ActiveObjectMap.Key key = new ActiveObjectMap.Key(id);
        return this.internalKeyToServant(key);
    }

    protected void activateServant(ActiveObjectMap.Key key, AOMEntry entry, Servant servant) {
        this.setDelegate(servant, key.id);
        if (this.orb.shutdownDebugFlag) {
            System.out.println("Activating object " + servant + " with POA " + this.poa);
        }
        this.activeObjectMap.putServant(servant, entry);
        if (Util.instance != null) {
            POAManagerImpl pm = (POAManagerImpl)this.poa.the_POAManager();
            POAFactory factory2 = pm.getFactory();
            factory2.registerPOAForServant(this.poa, servant);
        }
    }

    @Override
    public final void activateObject(byte[] id, Servant servant) throws WrongPolicy, ServantAlreadyActive, ObjectAlreadyActive {
        if (this.isUnique && this.activeObjectMap.contains(servant)) {
            throw new ServantAlreadyActive();
        }
        ActiveObjectMap.Key key = new ActiveObjectMap.Key(id);
        if (this.activeObjectMap.containsKey(key)) {
            throw new ObjectAlreadyActive();
        }
        AOMEntry entry = this.activeObjectMap.get(key);
        entry.activateObject();
        this.activateServant(key, entry, servant);
    }

    @Override
    public Servant deactivateObject(byte[] id) throws ObjectNotActive, WrongPolicy {
        ActiveObjectMap.Key key = new ActiveObjectMap.Key(id);
        return this.deactivateObject(key);
    }

    protected void deactivateHelper(ActiveObjectMap.Key key, AOMEntry entry, Servant s2) throws ObjectNotActive, WrongPolicy {
        this.activeObjectMap.remove(key);
        if (Util.instance != null) {
            POAManagerImpl pm = (POAManagerImpl)this.poa.the_POAManager();
            POAFactory factory2 = pm.getFactory();
            factory2.unregisterPOAForServant(this.poa, s2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Servant deactivateObject(ActiveObjectMap.Key key) throws ObjectNotActive, WrongPolicy {
        if (this.orb.poaDebugFlag) {
            ORBUtility.dprint(this, "Calling deactivateObject for key " + key);
        }
        try {
            AOMEntry entry = this.activeObjectMap.get(key);
            if (entry == null) {
                throw new ObjectNotActive();
            }
            Servant s2 = this.activeObjectMap.getServant(entry);
            if (s2 == null) {
                throw new ObjectNotActive();
            }
            if (this.orb.poaDebugFlag) {
                System.out.println("Deactivating object " + s2 + " with POA " + this.poa);
            }
            this.deactivateHelper(key, entry, s2);
            Servant servant = s2;
            return servant;
        }
        finally {
            if (this.orb.poaDebugFlag) {
                ORBUtility.dprint(this, "Exiting deactivateObject");
            }
        }
    }

    @Override
    public byte[] servantToId(Servant servant) throws ServantNotActive, WrongPolicy {
        ActiveObjectMap.Key key;
        if (!this.isUnique && !this.isImplicit) {
            throw new WrongPolicy();
        }
        if (this.isUnique && (key = this.activeObjectMap.getKey(servant)) != null) {
            return key.id;
        }
        if (this.isImplicit) {
            try {
                byte[] id = this.newSystemId();
                this.activateObject(id, servant);
                return id;
            }
            catch (ObjectAlreadyActive oaa) {
                throw this.poa.invocationWrapper().servantToIdOaa(oaa);
            }
            catch (ServantAlreadyActive s2) {
                throw this.poa.invocationWrapper().servantToIdSaa(s2);
            }
            catch (WrongPolicy w) {
                throw this.poa.invocationWrapper().servantToIdWp(w);
            }
        }
        throw new ServantNotActive();
    }
}

