/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.Set;
import org.jboss.com.sun.corba.se.impl.oa.NullServantImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.AOMEntry;
import org.jboss.com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAFactory;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAManagerImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorBase_R;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.oa.NullServant;
import org.jboss.com.sun.corba.se.spi.oa.OAInvocationInfo;
import org.omg.CORBA.SystemException;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantActivator;
import org.omg.PortableServer.ServantManager;

public class POAPolicyMediatorImpl_R_USM
extends POAPolicyMediatorBase_R {
    protected ServantActivator activator = null;

    POAPolicyMediatorImpl_R_USM(Policies policies, POAImpl poa) {
        super(policies, poa);
        if (!policies.useServantManager()) {
            throw poa.invocationWrapper().policyMediatorBadPolicyInFactory();
        }
    }

    private AOMEntry enterEntry(ActiveObjectMap.Key key) {
        boolean failed;
        AOMEntry result = null;
        do {
            failed = false;
            result = this.activeObjectMap.get(key);
            try {
                result.enter();
            }
            catch (Exception exc) {
                failed = true;
            }
        } while (failed);
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Object internalGetServant(byte[] id, String operation) throws ForwardRequest {
        if (this.poa.getDebug()) {
            ORBUtility.dprint(this, "Calling POAPolicyMediatorImpl_R_USM.internalGetServant for poa " + this.poa + " operation=" + operation);
        }
        try {
            ActiveObjectMap.Key key = new ActiveObjectMap.Key(id);
            AOMEntry entry = this.enterEntry(key);
            Object servant = this.activeObjectMap.getServant(entry);
            if (servant != null) {
                if (this.poa.getDebug()) {
                    ORBUtility.dprint(this, "internalGetServant: servant already activated");
                }
                Servant servant2 = servant;
                return servant2;
            }
            if (this.activator == null) {
                if (this.poa.getDebug()) {
                    ORBUtility.dprint(this, "internalGetServant: no servant activator in POA");
                }
                entry.incarnateFailure();
                throw this.poa.invocationWrapper().poaNoServantManager();
            }
            try {
                if (this.poa.getDebug()) {
                    ORBUtility.dprint(this, "internalGetServant: upcall to incarnate");
                }
                this.poa.unlock();
                servant = this.activator.incarnate(id, this.poa);
                if (servant == null) {
                    servant = new NullServantImpl(this.poa.omgInvocationWrapper().nullServantReturned());
                }
            }
            catch (ForwardRequest freq) {
                try {
                    if (!this.poa.getDebug()) throw freq;
                    ORBUtility.dprint(this, "internalGetServant: incarnate threw ForwardRequest");
                    throw freq;
                    catch (SystemException exc) {
                        if (!this.poa.getDebug()) throw exc;
                        ORBUtility.dprint(this, "internalGetServant: incarnate threw SystemException " + exc);
                        throw exc;
                    }
                    catch (Throwable exc) {
                        if (!this.poa.getDebug()) throw this.poa.invocationWrapper().poaServantActivatorLookupFailed(exc);
                        ORBUtility.dprint(this, "internalGetServant: incarnate threw Throwable " + exc);
                        throw this.poa.invocationWrapper().poaServantActivatorLookupFailed(exc);
                    }
                }
                catch (Throwable throwable) {
                    this.poa.lock();
                    if (servant == null || servant instanceof NullServant) {
                        if (this.poa.getDebug()) {
                            ORBUtility.dprint(this, "internalGetServant: incarnate failed");
                        }
                        entry.incarnateFailure();
                        throw throwable;
                    }
                    if (this.isUnique && this.activeObjectMap.contains((Servant)servant)) {
                        if (this.poa.getDebug()) {
                            ORBUtility.dprint(this, "internalGetServant: servant already assigned to ID");
                        }
                        entry.incarnateFailure();
                        throw this.poa.invocationWrapper().poaServantNotUnique();
                    }
                    if (this.poa.getDebug()) {
                        ORBUtility.dprint(this, "internalGetServant: incarnate complete");
                    }
                    entry.incarnateComplete();
                    this.activateServant(key, entry, (Servant)servant);
                    throw throwable;
                }
            }
            this.poa.lock();
            if (servant == null || servant instanceof NullServant) {
                if (this.poa.getDebug()) {
                    ORBUtility.dprint(this, "internalGetServant: incarnate failed");
                }
                entry.incarnateFailure();
            } else {
                if (this.isUnique && this.activeObjectMap.contains((Servant)servant)) {
                    if (this.poa.getDebug()) {
                        ORBUtility.dprint(this, "internalGetServant: servant already assigned to ID");
                    }
                    entry.incarnateFailure();
                    throw this.poa.invocationWrapper().poaServantNotUnique();
                }
                if (this.poa.getDebug()) {
                    ORBUtility.dprint(this, "internalGetServant: incarnate complete");
                }
                entry.incarnateComplete();
                this.activateServant(key, entry, (Servant)servant);
            }
            Object object = servant;
            return object;
        }
        finally {
            if (this.poa.getDebug()) {
                ORBUtility.dprint(this, "Exiting POAPolicyMediatorImpl_R_USM.internalGetServant for poa " + this.poa);
            }
        }
    }

    @Override
    public void returnServant() {
        OAInvocationInfo info = this.orb.peekInvocationInfo();
        byte[] id = info.id();
        ActiveObjectMap.Key key = new ActiveObjectMap.Key(id);
        AOMEntry entry = this.activeObjectMap.get(key);
        entry.exit();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void etherealizeAll() {
        if (this.activator != null) {
            Set<ActiveObjectMap.Key> keySet = this.activeObjectMap.keySet();
            ActiveObjectMap.Key[] keys2 = keySet.toArray(new ActiveObjectMap.Key[keySet.size()]);
            for (int ctr = 0; ctr < keySet.size(); ++ctr) {
                ActiveObjectMap.Key key = keys2[ctr];
                AOMEntry entry = this.activeObjectMap.get(key);
                Servant servant = this.activeObjectMap.getServant(entry);
                if (servant == null) continue;
                boolean remainingActivations = this.activeObjectMap.hasMultipleIDs(entry);
                entry.startEtherealize(null);
                try {
                    this.poa.unlock();
                    try {
                        this.activator.etherealize(key.id, this.poa, servant, true, remainingActivations);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    continue;
                }
                finally {
                    this.poa.lock();
                    entry.etherealizeComplete();
                }
            }
        }
    }

    @Override
    public ServantManager getServantManager() throws WrongPolicy {
        return this.activator;
    }

    @Override
    public void setServantManager(ServantManager servantManager) throws WrongPolicy {
        if (this.activator != null) {
            throw this.poa.invocationWrapper().servantManagerAlreadySet();
        }
        if (!(servantManager instanceof ServantActivator)) {
            throw this.poa.invocationWrapper().servantManagerBadType();
        }
        this.activator = (ServantActivator)servantManager;
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
    public void deactivateHelper(ActiveObjectMap.Key key, AOMEntry entry, Servant servant) throws ObjectNotActive, WrongPolicy {
        if (this.activator == null) {
            throw this.poa.invocationWrapper().poaNoServantManager();
        }
        Etherealizer eth = new Etherealizer(this, key, entry, servant, this.poa.getDebug());
        entry.startEtherealize(eth);
    }

    @Override
    public Servant idToServant(byte[] id) throws WrongPolicy, ObjectNotActive {
        ActiveObjectMap.Key key = new ActiveObjectMap.Key(id);
        AOMEntry entry = this.activeObjectMap.get(key);
        Servant servant = this.activeObjectMap.getServant(entry);
        if (servant != null) {
            return servant;
        }
        throw new ObjectNotActive();
    }

    class Etherealizer
    extends Thread {
        private POAPolicyMediatorImpl_R_USM mediator;
        private ActiveObjectMap.Key key;
        private AOMEntry entry;
        private Servant servant;
        private boolean debug;

        public Etherealizer(POAPolicyMediatorImpl_R_USM mediator, ActiveObjectMap.Key key, AOMEntry entry, Servant servant, boolean debug) {
            this.mediator = mediator;
            this.key = key;
            this.entry = entry;
            this.servant = servant;
            this.debug = debug;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            if (this.debug) {
                ORBUtility.dprint(this, "Calling Etherealizer.run on key " + this.key);
            }
            try {
                try {
                    this.mediator.activator.etherealize(this.key.id, this.mediator.poa, this.servant, false, this.mediator.activeObjectMap.hasMultipleIDs(this.entry));
                }
                catch (Exception exception) {
                    // empty catch block
                }
                try {
                    this.mediator.poa.lock();
                    this.entry.etherealizeComplete();
                    this.mediator.activeObjectMap.remove(this.key);
                    POAManagerImpl pm = (POAManagerImpl)this.mediator.poa.the_POAManager();
                    POAFactory factory2 = pm.getFactory();
                    factory2.unregisterPOAForServant(this.mediator.poa, this.servant);
                }
                finally {
                    this.mediator.poa.unlock();
                }
            }
            finally {
                if (this.debug) {
                    ORBUtility.dprint(this, "Exiting Etherealizer.run");
                }
            }
        }
    }
}

