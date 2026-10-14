/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.CondVar;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Action;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.ActionBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSMImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.GuardBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.InputImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.State;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.StateEngine;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.StateEngineFactory;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.StateImpl;

public class AOMEntry
extends FSMImpl {
    private final Thread[] etherealizer;
    private final int[] counter;
    private final CondVar wait;
    final POAImpl poa;
    public static final State INVALID = new StateImpl("Invalid");
    public static final State INCARN = new StateImpl("Incarnating"){

        @Override
        public void postAction(FSM fsm) {
            AOMEntry entry = (AOMEntry)fsm;
            entry.wait.broadcast();
        }
    };
    public static final State VALID = new StateImpl("Valid");
    public static final State ETHP = new StateImpl("EtherealizePending");
    public static final State ETH = new StateImpl("Etherealizing"){

        @Override
        public void preAction(FSM fsm) {
            AOMEntry entry = (AOMEntry)fsm;
            Thread etherealizer = entry.etherealizer[0];
            if (etherealizer != null) {
                etherealizer.start();
            }
        }

        @Override
        public void postAction(FSM fsm) {
            AOMEntry entry = (AOMEntry)fsm;
            entry.wait.broadcast();
        }
    };
    public static final State DESTROYED = new StateImpl("Destroyed");
    static final Input START_ETH = new InputImpl("startEtherealize");
    static final Input ETH_DONE = new InputImpl("etherealizeDone");
    static final Input INC_DONE = new InputImpl("incarnateDone");
    static final Input INC_FAIL = new InputImpl("incarnateFailure");
    static final Input ACTIVATE = new InputImpl("activateObject");
    static final Input ENTER = new InputImpl("enter");
    static final Input EXIT = new InputImpl("exit");
    private static Action incrementAction = new ActionBase("increment"){

        @Override
        public void doIt(FSM fsm, Input in) {
            AOMEntry entry = (AOMEntry)fsm;
            int[] nArray = entry.counter;
            nArray[0] = nArray[0] + 1;
        }
    };
    private static Action decrementAction = new ActionBase("decrement"){

        @Override
        public void doIt(FSM fsm, Input in) {
            AOMEntry entry = (AOMEntry)fsm;
            if (entry.counter[0] <= 0) {
                throw entry.poa.lifecycleWrapper().aomEntryDecZero();
            }
            int[] nArray = entry.counter;
            nArray[0] = nArray[0] - 1;
        }
    };
    private static Action throwIllegalStateExceptionAction = new ActionBase("throwIllegalStateException"){

        @Override
        public void doIt(FSM fsm, Input in) {
            throw new IllegalStateException("No transitions allowed from the DESTROYED state");
        }
    };
    private static Guard waitGuard = new GuardBase("wait"){

        @Override
        public Guard.Result evaluate(FSM fsm, Input in) {
            AOMEntry entry = (AOMEntry)fsm;
            try {
                entry.wait.await();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            return Guard.Result.DEFERED;
        }
    };
    private static GuardBase greaterZeroGuard = new CounterGuard(0);
    private static Guard zeroGuard = new Guard.Complement(greaterZeroGuard);
    private static GuardBase greaterOneGuard = new CounterGuard(1);
    private static Guard oneGuard = new Guard.Complement(greaterOneGuard);
    private static StateEngine engine = StateEngineFactory.create();

    public AOMEntry(POAImpl poa) {
        super(engine, INVALID, poa.getORB().poaFSMDebugFlag);
        this.poa = poa;
        this.etherealizer = new Thread[1];
        this.etherealizer[0] = null;
        this.counter = new int[1];
        this.counter[0] = 0;
        this.wait = new CondVar(poa.poaMutex, poa.getORB().poaConcurrencyDebugFlag);
    }

    public void startEtherealize(Thread etherealizer) {
        this.etherealizer[0] = etherealizer;
        this.doIt(START_ETH);
    }

    public void etherealizeComplete() {
        this.doIt(ETH_DONE);
    }

    public void incarnateComplete() {
        this.doIt(INC_DONE);
    }

    public void incarnateFailure() {
        this.doIt(INC_FAIL);
    }

    public void activateObject() {
        this.doIt(ACTIVATE);
    }

    public void enter() {
        this.doIt(ENTER);
    }

    public void exit() {
        this.doIt(EXIT);
    }

    static {
        engine.add(INVALID, ENTER, incrementAction, INCARN);
        engine.add(INVALID, ACTIVATE, null, VALID);
        engine.setDefault(INVALID);
        engine.add(INCARN, ENTER, waitGuard, null, INCARN);
        engine.add(INCARN, EXIT, null, INCARN);
        engine.add(INCARN, START_ETH, waitGuard, null, INCARN);
        engine.add(INCARN, INC_DONE, null, VALID);
        engine.add(INCARN, INC_FAIL, decrementAction, INVALID);
        engine.add(VALID, ENTER, incrementAction, VALID);
        engine.add(VALID, EXIT, decrementAction, VALID);
        engine.add(VALID, START_ETH, greaterZeroGuard, null, ETHP);
        engine.add(VALID, START_ETH, zeroGuard, null, ETH);
        engine.add(ETHP, ENTER, waitGuard, null, ETHP);
        engine.add(ETHP, START_ETH, null, ETHP);
        engine.add(ETHP, EXIT, greaterOneGuard, decrementAction, ETHP);
        engine.add(ETHP, EXIT, oneGuard, decrementAction, ETH);
        engine.add(ETH, START_ETH, null, ETH);
        engine.add(ETH, ETH_DONE, null, DESTROYED);
        engine.add(ETH, ENTER, waitGuard, null, ETH);
        engine.setDefault(DESTROYED, throwIllegalStateExceptionAction, DESTROYED);
        engine.done();
    }

    private static class CounterGuard
    extends GuardBase {
        private int value;

        public CounterGuard(int value) {
            super("counter>" + value);
            this.value = value;
        }

        @Override
        public Guard.Result evaluate(FSM fsm, Input in) {
            AOMEntry entry = (AOMEntry)fsm;
            return Guard.Result.convert(entry.counter[0] > this.value);
        }
    }
}

