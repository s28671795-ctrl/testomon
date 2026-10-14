/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.POASystemException;
import org.jboss.com.sun.corba.se.impl.oa.poa.DelegateImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POACurrent;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapter;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapterFactory;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orbutil.closure.Closure;
import org.jboss.com.sun.corba.se.spi.orbutil.closure.ClosureFactory;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CORBA.TRANSIENT;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.portable.Delegate;

public class POAFactory
implements ObjectAdapterFactory {
    private Map<Servant, POA> exportedServantsToPOA = new WeakHashMap<Servant, POA>();
    private Set<POAManager> poaManagers = Collections.synchronizedSet(new HashSet(4));
    private int poaManagerId = 0;
    private int poaId = 0;
    private POAImpl rootPOA = null;
    private DelegateImpl delegateImpl = null;
    private ORB orb = null;
    private POASystemException wrapper;
    private OMGSystemException omgWrapper;
    private boolean isShuttingDown = false;

    public POASystemException getWrapper() {
        return this.wrapper;
    }

    public synchronized POA lookupPOA(Servant servant) {
        return this.exportedServantsToPOA.get(servant);
    }

    public synchronized void registerPOAForServant(POA poa, Servant servant) {
        this.exportedServantsToPOA.put(servant, poa);
    }

    public synchronized void unregisterPOAForServant(POA poa, Servant servant) {
        this.exportedServantsToPOA.remove(servant);
    }

    @Override
    public void init(ORB orb) {
        this.orb = orb;
        this.wrapper = POASystemException.get(orb, "oa.lifecycle");
        this.omgWrapper = OMGSystemException.get(orb, "oa.lifecycle");
        this.delegateImpl = new DelegateImpl(orb, this);
        this.registerRootPOA();
        POACurrent poaCurrent = new POACurrent(orb);
        orb.getLocalResolver().register("POACurrent", ClosureFactory.makeConstant(poaCurrent));
    }

    @Override
    public ObjectAdapter find(ObjectAdapterId oaid) {
        POA poa = null;
        try {
            boolean first = true;
            Iterator<String> iter = oaid.iterator();
            poa = this.getRootPOA();
            while (iter.hasNext()) {
                String name = iter.next();
                if (first) {
                    if (!name.equals("RootPOA")) {
                        throw this.wrapper.makeFactoryNotPoa(name);
                    }
                    first = false;
                    continue;
                }
                poa = poa.find_POA(name, true);
            }
        }
        catch (AdapterNonExistent ex) {
            throw this.omgWrapper.noObjectAdaptor(ex);
        }
        catch (OBJECT_NOT_EXIST ex) {
            throw ex;
        }
        catch (TRANSIENT ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw this.wrapper.poaLookupError(ex);
        }
        if (poa == null) {
            throw this.wrapper.poaLookupError();
        }
        return (ObjectAdapter)((java.lang.Object)poa);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown(boolean waitForCompletion) {
        Iterator<POAManager> managers = null;
        POAFactory pOAFactory = this;
        synchronized (pOAFactory) {
            this.isShuttingDown = true;
            managers = new HashSet<POAManager>(this.poaManagers).iterator();
        }
        while (managers.hasNext()) {
            try {
                managers.next().deactivate(true, waitForCompletion);
            }
            catch (AdapterInactive adapterInactive) {}
        }
    }

    public synchronized void removePoaManager(POAManager manager) {
        this.poaManagers.remove(manager);
    }

    public synchronized void addPoaManager(POAManager manager) {
        this.poaManagers.add(manager);
    }

    public synchronized int newPOAManagerId() {
        return this.poaManagerId++;
    }

    public void registerRootPOA() {
        Closure rpClosure = new Closure(){

            @Override
            public java.lang.Object evaluate() {
                return POAImpl.makeRootPOA(POAFactory.this.orb);
            }
        };
        this.orb.getLocalResolver().register("RootPOA", ClosureFactory.makeFuture(rpClosure));
    }

    public synchronized POA getRootPOA() {
        if (this.rootPOA == null) {
            if (this.isShuttingDown) {
                throw this.omgWrapper.noObjectAdaptor();
            }
            try {
                Object obj = this.orb.resolve_initial_references("RootPOA");
                this.rootPOA = (POAImpl)obj;
            }
            catch (InvalidName inv) {
                throw this.wrapper.cantResolveRootPoa(inv);
            }
        }
        return this.rootPOA;
    }

    public Delegate getDelegateImpl() {
        return this.delegateImpl;
    }

    public synchronized int newPOAId() {
        return this.poaId++;
    }

    @Override
    public ORB getORB() {
        return this.orb;
    }
}

