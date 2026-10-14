/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jboss.com.sun.corba.se.impl.logging.POASystemException;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAFactory;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.protocol.PIHandler;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAManagerPackage.State;

public class POAManagerImpl
extends LocalObject
implements POAManager {
    private static final long serialVersionUID = 5295604954267836516L;
    private final POAFactory factory;
    private PIHandler pihandler;
    private State state;
    private Set<POA> poas = new HashSet<POA>(4);
    private int nInvocations = 0;
    private int nWaiters = 0;
    private int myId = 0;
    private boolean debug;
    private boolean explicitStateChange;

    private String stateToString(State state) {
        switch (state.value()) {
            case 0: {
                return "State[HOLDING]";
            }
            case 1: {
                return "State[ACTIVE]";
            }
            case 2: {
                return "State[DISCARDING]";
            }
            case 3: {
                return "State[INACTIVE]";
            }
        }
        return "State[UNKNOWN]";
    }

    public String toString() {
        return "POAManagerImpl[myId=" + this.myId + " state=" + this.stateToString(this.state) + " nInvocations=" + this.nInvocations + " nWaiters=" + this.nWaiters + "]";
    }

    POAFactory getFactory() {
        return this.factory;
    }

    PIHandler getPIHandler() {
        return this.pihandler;
    }

    private void countedWait() {
        try {
            if (this.debug) {
                ORBUtility.dprint(this, "Calling countedWait on POAManager " + this + " nWaiters=" + this.nWaiters);
            }
            ++this.nWaiters;
            this.wait();
        }
        catch (InterruptedException interruptedException) {
        }
        finally {
            --this.nWaiters;
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting countedWait on POAManager " + this + " nWaiters=" + this.nWaiters);
            }
        }
    }

    private void notifyWaiters() {
        if (this.debug) {
            ORBUtility.dprint(this, "Calling notifyWaiters on POAManager " + this + " nWaiters=" + this.nWaiters);
        }
        if (this.nWaiters > 0) {
            this.notifyAll();
        }
    }

    public int getManagerId() {
        return this.myId;
    }

    POAManagerImpl(POAFactory factory2, PIHandler pihandler) {
        this.factory = factory2;
        factory2.addPoaManager(this);
        this.pihandler = pihandler;
        this.myId = factory2.newPOAManagerId();
        this.state = State.HOLDING;
        this.debug = factory2.getORB().poaDebugFlag;
        this.explicitStateChange = false;
        if (this.debug) {
            ORBUtility.dprint(this, "Creating POAManagerImpl " + this);
        }
    }

    synchronized void addPOA(POA poa) {
        if (this.state.value() == 3) {
            POASystemException wrapper = this.factory.getWrapper();
            throw wrapper.addPoaInactive(CompletionStatus.COMPLETED_NO);
        }
        this.poas.add(poa);
    }

    synchronized void removePOA(POA poa) {
        this.poas.remove(poa);
        if (this.poas.isEmpty()) {
            this.factory.removePoaManager(this);
        }
    }

    public short getORTState() {
        switch (this.state.value()) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 3: {
                return 3;
            }
            case 2: {
                return 2;
            }
        }
        return 4;
    }

    @Override
    public synchronized void activate() throws AdapterInactive {
        this.explicitStateChange = true;
        if (this.debug) {
            ORBUtility.dprint(this, "Calling activate on POAManager " + this);
        }
        try {
            if (this.state.value() == 3) {
                throw new AdapterInactive();
            }
            this.state = State.ACTIVE;
            this.pihandler.adapterManagerStateChanged(this.myId, this.getORTState());
            this.notifyWaiters();
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting activate on POAManager " + this);
            }
        }
    }

    @Override
    public synchronized void hold_requests(boolean wait_for_completion) throws AdapterInactive {
        this.explicitStateChange = true;
        if (this.debug) {
            ORBUtility.dprint(this, "Calling hold_requests on POAManager " + this);
        }
        try {
            if (this.state.value() == 3) {
                throw new AdapterInactive();
            }
            this.state = State.HOLDING;
            this.pihandler.adapterManagerStateChanged(this.myId, this.getORTState());
            this.notifyWaiters();
            if (wait_for_completion) {
                while (this.state.value() == 0 && this.nInvocations > 0) {
                    this.countedWait();
                }
            }
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting hold_requests on POAManager " + this);
            }
        }
    }

    @Override
    public synchronized void discard_requests(boolean wait_for_completion) throws AdapterInactive {
        this.explicitStateChange = true;
        if (this.debug) {
            ORBUtility.dprint(this, "Calling hold_requests on POAManager " + this);
        }
        try {
            if (this.state.value() == 3) {
                throw new AdapterInactive();
            }
            this.state = State.DISCARDING;
            this.pihandler.adapterManagerStateChanged(this.myId, this.getORTState());
            this.notifyWaiters();
            if (wait_for_completion) {
                while (this.state.value() == 2 && this.nInvocations > 0) {
                    this.countedWait();
                }
            }
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting hold_requests on POAManager " + this);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deactivate(boolean etherealize_objects, boolean wait_for_completion) throws AdapterInactive {
        POAManagerImpl pOAManagerImpl;
        this.explicitStateChange = true;
        try {
            pOAManagerImpl = this;
            synchronized (pOAManagerImpl) {
                if (this.debug) {
                    ORBUtility.dprint(this, "Calling deactivate on POAManager " + this);
                }
                if (this.state.value() == 3) {
                    throw new AdapterInactive();
                }
                this.state = State.INACTIVE;
                this.pihandler.adapterManagerStateChanged(this.myId, this.getORTState());
                this.notifyWaiters();
            }
            POAManagerDeactivator deactivator = new POAManagerDeactivator(this, etherealize_objects, this.debug);
            if (wait_for_completion) {
                deactivator.run();
            } else {
                Thread thr = new Thread(deactivator);
                thr.start();
            }
        }
        finally {
            pOAManagerImpl = this;
            synchronized (pOAManagerImpl) {
                if (this.debug) {
                    ORBUtility.dprint(this, "Exiting deactivate on POAManager " + this);
                }
            }
        }
    }

    @Override
    public State get_state() {
        return this.state;
    }

    synchronized void checkIfActive() {
        try {
            if (this.debug) {
                ORBUtility.dprint(this, "Calling checkIfActive for POAManagerImpl " + this);
            }
            this.checkState();
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting checkIfActive for POAManagerImpl " + this);
            }
        }
    }

    private void checkState() {
        while (this.state.value() != 1) {
            switch (this.state.value()) {
                case 0: {
                    while (this.state.value() == 0) {
                        this.countedWait();
                    }
                    break;
                }
                case 2: {
                    throw this.factory.getWrapper().poaDiscarding();
                }
                case 3: {
                    throw this.factory.getWrapper().poaInactive();
                }
            }
        }
    }

    synchronized void enter() {
        try {
            if (this.debug) {
                ORBUtility.dprint(this, "Calling enter for POAManagerImpl " + this);
            }
            this.checkState();
            ++this.nInvocations;
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting enter for POAManagerImpl " + this);
            }
        }
    }

    synchronized void exit() {
        try {
            if (this.debug) {
                ORBUtility.dprint(this, "Calling exit for POAManagerImpl " + this);
            }
            --this.nInvocations;
            if (this.nInvocations == 0) {
                this.notifyWaiters();
            }
        }
        finally {
            if (this.debug) {
                ORBUtility.dprint(this, "Exiting exit for POAManagerImpl " + this);
            }
        }
    }

    public synchronized void implicitActivation() {
        if (!this.explicitStateChange) {
            try {
                this.activate();
            }
            catch (AdapterInactive adapterInactive) {
                // empty catch block
            }
        }
    }

    private class POAManagerDeactivator
    implements Runnable {
        private boolean etherealize_objects;
        private POAManagerImpl pmi;
        private boolean debug;

        POAManagerDeactivator(POAManagerImpl pmi, boolean etherealize_objects, boolean debug) {
            this.etherealize_objects = etherealize_objects;
            this.pmi = pmi;
            this.debug = debug;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            block24: {
                POAManagerImpl pOAManagerImpl;
                try {
                    pOAManagerImpl = this.pmi;
                    synchronized (pOAManagerImpl) {
                        if (this.debug) {
                            ORBUtility.dprint(this, "Calling run with etherealize_objects=" + this.etherealize_objects + " pmi=" + this.pmi);
                        }
                        while (this.pmi.nInvocations > 0) {
                            POAManagerImpl.this.countedWait();
                        }
                    }
                    if (!this.etherealize_objects) break block24;
                    Iterator iterator2 = null;
                    POAManagerImpl pOAManagerImpl2 = this.pmi;
                    synchronized (pOAManagerImpl2) {
                        if (this.debug) {
                            ORBUtility.dprint(this, "run: Preparing to etherealize with pmi=" + this.pmi);
                        }
                        iterator2 = new HashSet(this.pmi.poas).iterator();
                    }
                    while (iterator2.hasNext()) {
                        ((POAImpl)iterator2.next()).etherealizeAll();
                    }
                    pOAManagerImpl2 = this.pmi;
                    synchronized (pOAManagerImpl2) {
                        if (this.debug) {
                            ORBUtility.dprint(this, "run: removing POAManager and clearing poas with pmi=" + this.pmi);
                        }
                        POAManagerImpl.this.factory.removePoaManager(this.pmi);
                        POAManagerImpl.this.poas.clear();
                    }
                }
                finally {
                    if (this.debug) {
                        pOAManagerImpl = this.pmi;
                        synchronized (pOAManagerImpl) {
                            ORBUtility.dprint(this, "Exiting run");
                        }
                    }
                }
            }
        }
    }
}

