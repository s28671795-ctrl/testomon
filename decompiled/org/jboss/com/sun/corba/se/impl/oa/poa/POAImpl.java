/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jboss.com.sun.corba.se.impl.ior.ObjectAdapterIdArray;
import org.jboss.com.sun.corba.se.impl.ior.POAObjectKeyTemplate;
import org.jboss.com.sun.corba.se.impl.oa.poa.IdAssignmentPolicyImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.IdUniquenessPolicyImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.ImplicitActivationPolicyImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.LifespanPolicyImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAFactory;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAManagerImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediator;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorFactory;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.jboss.com.sun.corba.se.impl.oa.poa.RequestProcessingPolicyImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.ServantRetentionPolicyImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.ThreadPolicyImpl;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.CondVar;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.ReentrantMutex;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.Sync;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.SyncUtil;
import org.jboss.com.sun.corba.se.spi.copyobject.CopierManager;
import org.jboss.com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplateList;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfile;
import org.jboss.com.sun.corba.se.spi.oa.OADestroyed;
import org.jboss.com.sun.corba.se.spi.oa.OAInvocationInfo;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapterBase;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.protocol.ForwardException;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.SystemException;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;
import org.omg.PortableServer.AdapterActivator;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyValue;

public class POAImpl
extends ObjectAdapterBase
implements POA {
    private static final long serialVersionUID = 1806031312673382020L;
    private boolean debug;
    private static final int STATE_START = 0;
    private static final int STATE_INIT = 1;
    private static final int STATE_INIT_DONE = 2;
    private static final int STATE_RUN = 3;
    private static final int STATE_DESTROYING = 4;
    private static final int STATE_DESTROYED = 5;
    private int state;
    private POAPolicyMediator mediator;
    private int numLevels;
    private ObjectAdapterId poaId;
    private String name;
    private POAManagerImpl manager;
    private int uniquePOAId;
    private POAImpl parent;
    private Map<String, POA> children;
    private AdapterActivator activator;
    private int invocationCount;
    Sync poaMutex;
    private CondVar adapterActivatorCV;
    private CondVar invokeCV;
    private CondVar beingDestroyedCV;
    protected ThreadLocal<java.lang.Object> isDestroying;

    private String stateToString() {
        switch (this.state) {
            case 0: {
                return "START";
            }
            case 1: {
                return "INIT";
            }
            case 2: {
                return "INIT_DONE";
            }
            case 3: {
                return "RUN";
            }
            case 4: {
                return "DESTROYING";
            }
            case 5: {
                return "DESTROYED";
            }
        }
        return "UNKNOWN(" + this.state + ")";
    }

    public String toString() {
        return "POA[" + this.poaId.toString() + ", uniquePOAId=" + this.uniquePOAId + ", state=" + this.stateToString() + ", invocationCount=" + this.invocationCount + "]";
    }

    boolean getDebug() {
        return this.debug;
    }

    static POAFactory getPOAFactory(ORB orb) {
        return (POAFactory)orb.getRequestDispatcherRegistry().getObjectAdapterFactory(32);
    }

    static POAImpl makeRootPOA(ORB orb) {
        POAManagerImpl poaManager = new POAManagerImpl(POAImpl.getPOAFactory(orb), orb.getPIHandler());
        POAImpl result = new POAImpl("RootPOA", null, orb, 0);
        result.initialize(poaManager, Policies.rootPOAPolicies);
        return result;
    }

    int getPOAId() {
        return this.uniquePOAId;
    }

    void lock() {
        SyncUtil.acquire(this.poaMutex);
        if (this.debug) {
            ORBUtility.dprint(this, "LOCKED poa " + this);
        }
    }

    void unlock() {
        if (this.debug) {
            ORBUtility.dprint(this, "UNLOCKED poa " + this);
        }
        this.poaMutex.release();
    }

    Policies getPolicies() {
        return this.mediator.getPolicies();
    }

    private POAImpl(String name, POAImpl parent, ORB orb, int initialState) {
        super(orb);
        this.debug = orb.poaDebugFlag;
        if (this.debug) {
            ORBUtility.dprint(this, "Creating POA with name=" + name + " parent=" + parent);
        }
        this.state = initialState;
        this.name = name;
        this.parent = parent;
        this.children = new HashMap<String, POA>();
        this.activator = null;
        this.uniquePOAId = POAImpl.getPOAFactory(orb).newPOAId();
        if (parent == null) {
            this.numLevels = 1;
        } else {
            this.numLevels = parent.numLevels + 1;
            parent.children.put(name, this);
        }
        String[] names = new String[this.numLevels];
        POAImpl poaImpl = this;
        int ctr = this.numLevels - 1;
        while (poaImpl != null) {
            names[ctr--] = poaImpl.name;
            poaImpl = poaImpl.parent;
        }
        this.poaId = new ObjectAdapterIdArray(names);
        this.invocationCount = 0;
        this.poaMutex = new ReentrantMutex(orb.poaConcurrencyDebugFlag);
        this.adapterActivatorCV = new CondVar(this.poaMutex, orb.poaConcurrencyDebugFlag);
        this.invokeCV = new CondVar(this.poaMutex, orb.poaConcurrencyDebugFlag);
        this.beingDestroyedCV = new CondVar(this.poaMutex, orb.poaConcurrencyDebugFlag);
        this.isDestroying = new ThreadLocal<java.lang.Object>(){

            @Override
            protected java.lang.Object initialValue() {
                return Boolean.FALSE;
            }
        };
    }

    private void initialize(POAManagerImpl manager, Policies policies) {
        if (this.debug) {
            ORBUtility.dprint(this, "Initializing poa " + this + " with POAManager=" + manager + " policies=" + policies);
        }
        this.manager = manager;
        manager.addPOA(this);
        this.mediator = POAPolicyMediatorFactory.create(policies, this);
        int serverid = this.mediator.getServerId();
        int scid = this.mediator.getScid();
        String orbId = this.getORB().getORBData().getORBId();
        POAObjectKeyTemplate oktemp = new POAObjectKeyTemplate(this.getORB(), scid, serverid, orbId, this.poaId);
        if (this.debug) {
            ORBUtility.dprint(this, "Initializing poa: oktemp=" + oktemp);
        }
        boolean objectAdapterCreated = true;
        this.initializeTemplate(oktemp, objectAdapterCreated, policies, null, null, oktemp.getObjectAdapterId());
        if (this.state == 0) {
            this.state = 3;
        } else if (this.state == 1) {
            this.state = 2;
        } else {
            throw this.lifecycleWrapper().illegalPoaStateTrans();
        }
    }

    private boolean waitUntilRunning() {
        if (this.debug) {
            ORBUtility.dprint(this, "Calling waitUntilRunning on poa " + this);
        }
        while (this.state < 3) {
            try {
                this.adapterActivatorCV.await();
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.debug) {
            ORBUtility.dprint(this, "Exiting waitUntilRunning on poa " + this);
        }
        return this.state == 3;
    }

    private boolean destroyIfNotInitDone() {
        try {
            boolean success;
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling destroyIfNotInitDone on poa " + this);
            }
            boolean bl = success = this.state == 2;
            if (success) {
                this.state = 3;
            } else {
                DestroyThread destroyer = new DestroyThread(false, this.debug);
                destroyer.doIt(this, true);
            }
            boolean bl2 = success;
            return bl2;
        }
        finally {
            this.adapterActivatorCV.broadcast();
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting destroyIfNotInitDone on poa " + this);
            }
            this.unlock();
        }
    }

    private byte[] internalReferenceToId(Object reference) throws WrongAdapter {
        IOR ior = ORBUtility.getIOR(reference);
        IORTemplateList thisTemplate = ior.getIORTemplates();
        ObjectReferenceFactory orf = this.getCurrentFactory();
        IORTemplateList poaTemplate = IORFactories.getIORTemplateList(orf);
        if (!poaTemplate.isEquivalent(thisTemplate)) {
            throw new WrongAdapter();
        }
        Iterator iter = ior.iterator();
        if (!iter.hasNext()) {
            throw this.iorWrapper().noProfilesInIor();
        }
        TaggedProfile prof = (TaggedProfile)iter.next();
        ObjectId oid = prof.getObjectId();
        return oid.getId();
    }

    void etherealizeAll() {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling etheralizeAll on poa " + this);
            }
            this.mediator.etherealizeAll();
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting etheralizeAll on poa " + this);
            }
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public POA create_POA(String name, POAManager theManager, Policy[] policies) throws AdapterAlreadyExists, InvalidPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling create_POA(name=" + name + " theManager=" + theManager + " policies=" + policies + ") on poa " + this);
            }
            if (this.state > 3) {
                throw this.omgLifecycleWrapper().createPoaDestroy();
            }
            POAImpl poa = (POAImpl)this.children.get(name);
            if (poa == null) {
                poa = new POAImpl(name, this, this.getORB(), 0);
            }
            try {
                poa.lock();
                if (this.debug) {
                    ORBUtility.dprint(this, "Calling create_POA: new poa is " + poa);
                }
                if (poa.state != 0 && poa.state != 1) {
                    throw new AdapterAlreadyExists();
                }
                POAManagerImpl newManager = (POAManagerImpl)theManager;
                if (newManager == null) {
                    newManager = new POAManagerImpl(this.manager.getFactory(), this.manager.getPIHandler());
                }
                int defaultCopierId = this.getORB().getCopierManager().getDefaultId();
                Policies POAPolicies = new Policies(policies, defaultCopierId);
                poa.initialize(newManager, POAPolicies);
                POAImpl pOAImpl = poa;
                poa.unlock();
                return pOAImpl;
            }
            catch (Throwable throwable) {
                poa.unlock();
                throw throwable;
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public POA find_POA(String name, boolean activate) throws AdapterNonExistent {
        AdapterActivator act;
        POAImpl found;
        block29: {
            found = null;
            act = null;
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling find_POA(name=" + name + " activate=" + activate + ") on poa " + this);
            }
            if ((found = (POAImpl)this.children.get(name)) != null) {
                if (this.debug) {
                    ORBUtility.dprint(this, "Calling find_POA: found poa " + found);
                }
                try {
                    found.lock();
                    this.unlock();
                    if (!found.waitUntilRunning()) {
                        throw this.omgLifecycleWrapper().poaDestroyed();
                    }
                    break block29;
                }
                finally {
                    found.unlock();
                }
            }
            try {
                if (this.debug) {
                    ORBUtility.dprint(this, "Calling find_POA: no poa found");
                }
                if (activate && this.activator != null) {
                    found = new POAImpl(name, this, this.getORB(), 1);
                    if (this.debug) {
                        ORBUtility.dprint(this, "Calling find_POA: created poa " + found);
                    }
                    act = this.activator;
                    break block29;
                }
                throw new AdapterNonExistent();
            }
            finally {
                this.unlock();
            }
        }
        if (act != null) {
            boolean status = false;
            boolean adapterResult = false;
            if (this.debug) {
                ORBUtility.dprint(this, "Calling find_POA: calling AdapterActivator");
            }
            try {
                AdapterActivator adapterActivator = act;
                synchronized (adapterActivator) {
                    status = act.unknown_adapter(this, name);
                }
            }
            catch (SystemException exc) {
                throw this.omgLifecycleWrapper().adapterActivatorException(exc, (java.lang.Object)name, (java.lang.Object)this.poaId.toString());
            }
            catch (Throwable thr) {
                this.lifecycleWrapper().unexpectedException(thr, (java.lang.Object)this.toString());
                if (thr instanceof ThreadDeath) {
                    throw (ThreadDeath)thr;
                }
            }
            finally {
                adapterResult = found.destroyIfNotInitDone();
            }
            if (status) {
                if (!adapterResult) {
                    throw this.omgLifecycleWrapper().adapterActivatorException(name, this.poaId.toString());
                }
            } else {
                if (this.debug) {
                    ORBUtility.dprint(this, "Calling find_POA: AdapterActivator returned false");
                }
                throw new AdapterNonExistent();
            }
        }
        return found;
    }

    @Override
    public void destroy(boolean etherealize, boolean wait_for_completion) {
        if (wait_for_completion && this.getORB().isDuringDispatch()) {
            throw this.lifecycleWrapper().destroyDeadlock();
        }
        DestroyThread destroyer = new DestroyThread(etherealize, this.debug);
        destroyer.doIt(this, wait_for_completion);
    }

    @Override
    public ThreadPolicy create_thread_policy(ThreadPolicyValue value) {
        return new ThreadPolicyImpl(value);
    }

    @Override
    public LifespanPolicy create_lifespan_policy(LifespanPolicyValue value) {
        return new LifespanPolicyImpl(value);
    }

    @Override
    public IdUniquenessPolicy create_id_uniqueness_policy(IdUniquenessPolicyValue value) {
        return new IdUniquenessPolicyImpl(value);
    }

    @Override
    public IdAssignmentPolicy create_id_assignment_policy(IdAssignmentPolicyValue value) {
        return new IdAssignmentPolicyImpl(value);
    }

    @Override
    public ImplicitActivationPolicy create_implicit_activation_policy(ImplicitActivationPolicyValue value) {
        return new ImplicitActivationPolicyImpl(value);
    }

    @Override
    public ServantRetentionPolicy create_servant_retention_policy(ServantRetentionPolicyValue value) {
        return new ServantRetentionPolicyImpl(value);
    }

    @Override
    public RequestProcessingPolicy create_request_processing_policy(RequestProcessingPolicyValue value) {
        return new RequestProcessingPolicyImpl(value);
    }

    @Override
    public String the_name() {
        try {
            this.lock();
            String string = this.name;
            return string;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public POA the_parent() {
        try {
            this.lock();
            POAImpl pOAImpl = this.parent;
            return pOAImpl;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public POA[] the_children() {
        try {
            this.lock();
            Collection<POA> coll = this.children.values();
            int size = coll.size();
            POA[] result = new POA[size];
            int index = 0;
            for (POA poa : coll) {
                result[index++] = poa;
            }
            POA[] pOAArray = result;
            return pOAArray;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public POAManager the_POAManager() {
        try {
            this.lock();
            POAManagerImpl pOAManagerImpl = this.manager;
            return pOAManagerImpl;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public AdapterActivator the_activator() {
        try {
            this.lock();
            AdapterActivator adapterActivator = this.activator;
            return adapterActivator;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void the_activator(AdapterActivator activator) {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling the_activator on poa " + this + " activator=" + activator);
            }
            this.activator = activator;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public ServantManager get_servant_manager() throws WrongPolicy {
        try {
            this.lock();
            ServantManager servantManager = this.mediator.getServantManager();
            return servantManager;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void set_servant_manager(ServantManager servantManager) throws WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling set_servant_manager on poa " + this + " servantManager=" + servantManager);
            }
            this.mediator.setServantManager(servantManager);
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public Servant get_servant() throws NoServant, WrongPolicy {
        try {
            this.lock();
            Servant servant = this.mediator.getDefaultServant();
            return servant;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void set_servant(Servant defaultServant) throws WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling set_servant on poa " + this + " defaultServant=" + defaultServant);
            }
            this.mediator.setDefaultServant(defaultServant);
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public byte[] activate_object(Servant servant) throws ServantAlreadyActive, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling activate_object on poa " + this + " (servant=" + servant + ")");
            }
            byte[] id = this.mediator.newSystemId();
            try {
                this.mediator.activateObject(id, servant);
            }
            catch (ObjectAlreadyActive objectAlreadyActive) {
                // empty catch block
            }
            byte[] byArray = id;
            return byArray;
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting activate_object on poa " + this);
            }
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void activate_object_with_id(byte[] id, Servant servant) throws ObjectAlreadyActive, ServantAlreadyActive, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling activate_object_with_id on poa " + this + " (servant=" + servant + " id=" + id + ")");
            }
            byte[] idClone = (byte[])id.clone();
            this.mediator.activateObject(idClone, servant);
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting activate_object_with_id on poa " + this);
            }
            this.unlock();
        }
    }

    @Override
    public void deactivate_object(byte[] id) throws ObjectNotActive, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling deactivate_object on poa " + this + " (id=" + id + ")");
            }
            this.mediator.deactivateObject(id);
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting deactivate_object on poa " + this);
            }
            this.unlock();
        }
    }

    @Override
    public Object create_reference(String repId) throws WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling create_reference(repId=" + repId + ") on poa " + this);
            }
            Object object = this.makeObject(repId, this.mediator.newSystemId());
            return object;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object create_reference_with_id(byte[] oid, String repId) {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling create_reference_with_id(oid=" + oid + " repId=" + repId + ") on poa " + this);
            }
            byte[] idClone = (byte[])oid.clone();
            Object object = this.makeObject(repId, idClone);
            return object;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public byte[] servant_to_id(Servant servant) throws ServantNotActive, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling servant_to_id(servant=" + servant + ") on poa " + this);
            }
            byte[] byArray = this.mediator.servantToId(servant);
            return byArray;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object servant_to_reference(Servant servant) throws ServantNotActive, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling servant_to_reference(servant=" + servant + ") on poa " + this);
            }
            byte[] oid = this.mediator.servantToId(servant);
            String repId = servant._all_interfaces(this, oid)[0];
            Object object = this.create_reference_with_id(oid, repId);
            return object;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Servant reference_to_servant(Object reference) throws ObjectNotActive, WrongPolicy, WrongAdapter {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling reference_to_servant(reference=" + reference + ") on poa " + this);
            }
            if (this.state >= 4) {
                throw this.lifecycleWrapper().adapterDestroyed();
            }
            byte[] id = this.internalReferenceToId(reference);
            Servant servant = this.mediator.idToServant(id);
            return servant;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public byte[] reference_to_id(Object reference) throws WrongAdapter, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling reference_to_id(reference=" + reference + ") on poa " + this);
            }
            if (this.state >= 4) {
                throw this.lifecycleWrapper().adapterDestroyed();
            }
            byte[] byArray = this.internalReferenceToId(reference);
            return byArray;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public Servant id_to_servant(byte[] id) throws ObjectNotActive, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling id_to_servant(id=" + id + ") on poa " + this);
            }
            if (this.state >= 4) {
                throw this.lifecycleWrapper().adapterDestroyed();
            }
            Servant servant = this.mediator.idToServant(id);
            return servant;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object id_to_reference(byte[] id) throws ObjectNotActive, WrongPolicy {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling id_to_reference(id=" + id + ") on poa " + this);
            }
            if (this.state >= 4) {
                throw this.lifecycleWrapper().adapterDestroyed();
            }
            Servant s2 = this.mediator.idToServant(id);
            String repId = s2._all_interfaces(this, id)[0];
            Object object = this.makeObject(repId, id);
            return object;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public byte[] id() {
        try {
            this.lock();
            byte[] byArray = this.getAdapterId();
            return byArray;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public Policy getEffectivePolicy(int type) {
        return this.mediator.getPolicies().get_effective_policy(type);
    }

    @Override
    public int getManagerId() {
        return this.manager.getManagerId();
    }

    @Override
    public short getState() {
        return this.manager.getORTState();
    }

    @Override
    public String[] getInterfaces(java.lang.Object servant, byte[] objectId) {
        Servant serv = (Servant)servant;
        return serv._all_interfaces(this, objectId);
    }

    @Override
    protected ObjectCopierFactory getObjectCopierFactory() {
        int copierId = this.mediator.getPolicies().getCopierId();
        CopierManager cm = this.getORB().getCopierManager();
        return cm.getObjectCopierFactory(copierId);
    }

    @Override
    public void enter() throws OADestroyed {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling enter on poa " + this);
            }
            while (this.state == 4 && this.isDestroying.get() == Boolean.FALSE) {
                try {
                    this.beingDestroyedCV.await();
                }
                catch (InterruptedException interruptedException) {}
            }
            if (!this.waitUntilRunning()) {
                throw new OADestroyed();
            }
            ++this.invocationCount;
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting enter on poa " + this);
            }
            this.unlock();
        }
        this.manager.enter();
    }

    @Override
    public void exit() {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling exit on poa " + this);
            }
            --this.invocationCount;
            if (this.invocationCount == 0 && this.state == 4) {
                this.invokeCV.broadcast();
            }
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting exit on poa " + this);
            }
            this.unlock();
        }
        this.manager.exit();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void getInvocationServant(OAInvocationInfo info) {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling getInvocationServant on poa " + this);
            }
            java.lang.Object servant = null;
            try {
                servant = this.mediator.getInvocationServant(info.id(), info.getOperation());
            }
            catch (ForwardRequest freq) {
                throw new ForwardException(this.getORB(), freq.forward_reference);
            }
            info.setServant(servant);
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting getInvocationServant on poa " + this);
            }
            this.unlock();
        }
    }

    @Override
    public Object getLocalServant(byte[] objectId) {
        return null;
    }

    @Override
    public void returnServant() {
        try {
            this.lock();
            if (this.debug) {
                ORBUtility.dprint(this, "Calling returnServant on poa " + this);
            }
            this.mediator.returnServant();
        }
        catch (Throwable thr) {
            if (this.debug) {
                ORBUtility.dprint(this, "Exception " + thr + " in returnServant on poa " + this);
            }
            if (thr instanceof Error) {
                throw (Error)thr;
            }
            if (thr instanceof RuntimeException) {
                throw (RuntimeException)thr;
            }
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting returnServant on poa " + this);
            }
            this.unlock();
        }
    }

    static class DestroyThread
    extends Thread {
        private boolean wait;
        private boolean etherealize;
        private boolean debug;
        private POAImpl thePoa;

        public DestroyThread(boolean etherealize, boolean debug) {
            this.etherealize = etherealize;
            this.debug = debug;
        }

        public void doIt(POAImpl thePoa, boolean wait) {
            if (this.debug) {
                ORBUtility.dprint(this, "Calling DestroyThread.doIt(thePOA=" + thePoa + " wait=" + wait + " etherealize=" + this.etherealize);
            }
            this.thePoa = thePoa;
            this.wait = wait;
            if (wait) {
                this.run();
            } else {
                try {
                    this.setDaemon(true);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.start();
            }
        }

        @Override
        public void run() {
            HashSet<ObjectReferenceTemplate> destroyedPOATemplates = new HashSet<ObjectReferenceTemplate>();
            this.performDestroy(this.thePoa, destroyedPOATemplates);
            Iterator iter = destroyedPOATemplates.iterator();
            ObjectReferenceTemplate[] orts = new ObjectReferenceTemplate[destroyedPOATemplates.size()];
            int index = 0;
            while (iter.hasNext()) {
                orts[index++] = (ObjectReferenceTemplate)iter.next();
            }
            this.thePoa.getORB().getPIHandler().adapterStateChanged(orts, (short)4);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean prepareForDestruction(POAImpl poa, Set<ObjectReferenceTemplate> destroyedPOATemplates) {
            POAImpl[] childPoas = null;
            try {
                poa.lock();
                if (this.debug) {
                    ORBUtility.dprint(this, "Calling performDestroy on poa " + poa);
                }
                if (poa.state > 3) {
                    if (this.wait) {
                        while (poa.state != 5) {
                            try {
                                poa.beingDestroyedCV.await();
                            }
                            catch (InterruptedException interruptedException) {}
                        }
                    }
                    boolean bl = false;
                    return bl;
                }
                poa.state = 4;
                poa.isDestroying.set(Boolean.TRUE);
                childPoas = poa.children.values().toArray(new POAImpl[0]);
            }
            finally {
                poa.unlock();
            }
            for (int ctr = 0; ctr < childPoas.length; ++ctr) {
                this.performDestroy(childPoas[ctr], destroyedPOATemplates);
            }
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void performDestroy(POAImpl poa, Set<ObjectReferenceTemplate> destroyedPOATemplates) {
            if (!this.prepareForDestruction(poa, destroyedPOATemplates)) {
                return;
            }
            POAImpl parent = poa.parent;
            boolean isRoot = parent == null;
            try {
                if (!isRoot) {
                    parent.lock();
                }
                try {
                    poa.lock();
                    this.completeDestruction(poa, parent, destroyedPOATemplates);
                }
                finally {
                    poa.unlock();
                    if (isRoot) {
                        poa.manager.getFactory().registerRootPOA();
                    }
                }
            }
            finally {
                if (!isRoot) {
                    parent.unlock();
                    poa.parent = null;
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void completeDestruction(POAImpl poa, POAImpl parent, Set<ObjectReferenceTemplate> destroyedPOATemplates) {
            if (this.debug) {
                ORBUtility.dprint(this, "Calling completeDestruction on poa " + poa);
            }
            try {
                while (poa.invocationCount != 0) {
                    try {
                        poa.invokeCV.await();
                    }
                    catch (InterruptedException interruptedException) {}
                }
                if (poa.mediator != null) {
                    if (this.etherealize) {
                        poa.mediator.etherealizeAll();
                    }
                    poa.mediator.clearAOM();
                }
                if (poa.manager != null) {
                    poa.manager.removePOA(poa);
                }
                if (parent != null) {
                    parent.children.remove(poa.name);
                }
                destroyedPOATemplates.add(poa.getAdapterTemplate());
            }
            catch (Throwable thr) {
                if (thr instanceof ThreadDeath) {
                    throw (ThreadDeath)thr;
                }
                poa.lifecycleWrapper().unexpectedException(thr, (java.lang.Object)poa.toString());
            }
            finally {
                poa.state = 5;
                poa.beingDestroyedCV.broadcast();
                poa.isDestroying.set(Boolean.FALSE);
                if (this.debug) {
                    ORBUtility.dprint(this, "Exiting completeDestruction on poa " + poa);
                }
            }
        }
    }
}

