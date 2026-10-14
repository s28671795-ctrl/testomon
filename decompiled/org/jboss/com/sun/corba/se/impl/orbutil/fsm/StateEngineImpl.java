/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.fsm;

import java.util.Set;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.fsm.GuardedAction;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Action;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.ActionBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSMImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.State;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.StateEngine;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.StateImpl;
import org.omg.CORBA.INTERNAL;

public class StateEngineImpl
implements StateEngine {
    private static Action emptyAction = new ActionBase("Empty"){

        @Override
        public void doIt(FSM fsm, Input in) {
        }
    };
    private boolean initializing = true;
    private Action defaultAction = new ActionBase("Invalid Transition"){

        @Override
        public void doIt(FSM fsm, Input in) {
            throw new INTERNAL("Invalid transition attempted from " + fsm.getState() + " under " + in);
        }
    };

    @Override
    public StateEngine add(State oldState, Input input, Guard guard, Action action, State newState) throws IllegalArgumentException, IllegalStateException {
        this.mustBeInitializing();
        StateImpl oldStateImpl = (StateImpl)oldState;
        GuardedAction ga = new GuardedAction(guard, action, newState);
        oldStateImpl.addGuardedAction(input, ga);
        return this;
    }

    @Override
    public StateEngine add(State oldState, Input input, Action action, State newState) throws IllegalArgumentException, IllegalStateException {
        this.mustBeInitializing();
        StateImpl oldStateImpl = (StateImpl)oldState;
        GuardedAction ta = new GuardedAction(action, newState);
        oldStateImpl.addGuardedAction(input, ta);
        return this;
    }

    @Override
    public StateEngine setDefault(State oldState, Action action, State newState) throws IllegalArgumentException, IllegalStateException {
        this.mustBeInitializing();
        StateImpl oldStateImpl = (StateImpl)oldState;
        oldStateImpl.setDefaultAction(action);
        oldStateImpl.setDefaultNextState(newState);
        return this;
    }

    @Override
    public StateEngine setDefault(State oldState, State newState) throws IllegalArgumentException, IllegalStateException {
        return this.setDefault(oldState, emptyAction, newState);
    }

    @Override
    public StateEngine setDefault(State oldState) throws IllegalArgumentException, IllegalStateException {
        return this.setDefault(oldState, oldState);
    }

    @Override
    public void done() throws IllegalStateException {
        this.mustBeInitializing();
        this.initializing = false;
    }

    @Override
    public void setDefaultAction(Action act) throws IllegalStateException {
        this.mustBeInitializing();
        this.defaultAction = act;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doIt(FSM fsm, Input in, boolean debug) {
        if (debug) {
            ORBUtility.dprint(this, "doIt enter: currentState = " + fsm.getState() + " in = " + in);
        }
        try {
            this.innerDoIt(fsm, in, debug);
        }
        finally {
            if (debug) {
                ORBUtility.dprint(this, "doIt exit");
            }
        }
    }

    private StateImpl getDefaultNextState(StateImpl currentState) {
        StateImpl nextState = (StateImpl)currentState.getDefaultNextState();
        if (nextState == null) {
            nextState = currentState;
        }
        return nextState;
    }

    private Action getDefaultAction(StateImpl currentState) {
        Action action = currentState.getDefaultAction();
        if (action == null) {
            action = this.defaultAction;
        }
        return action;
    }

    private void innerDoIt(FSM fsm, Input in, boolean debug) {
        if (debug) {
            ORBUtility.dprint(this, "Calling innerDoIt with input " + in);
        }
        StateImpl currentState = null;
        StateImpl nextState = null;
        Action action = null;
        boolean deferral = false;
        block0: do {
            Set<GuardedAction> gas;
            deferral = false;
            currentState = (StateImpl)fsm.getState();
            nextState = this.getDefaultNextState(currentState);
            action = this.getDefaultAction(currentState);
            if (debug) {
                ORBUtility.dprint(this, "currentState      = " + currentState);
                ORBUtility.dprint(this, "in                = " + in);
                ORBUtility.dprint(this, "default nextState = " + nextState);
                ORBUtility.dprint(this, "default action    = " + action);
            }
            if ((gas = currentState.getGuardedActions(in)) == null) continue;
            for (GuardedAction ga : gas) {
                Guard.Result gr = ga.getGuard().evaluate(fsm, in);
                if (debug) {
                    ORBUtility.dprint(this, "doIt: evaluated " + ga + " with result " + gr);
                }
                if (gr == Guard.Result.ENABLED) {
                    nextState = (StateImpl)ga.getNextState();
                    action = ga.getAction();
                    if (!debug) continue block0;
                    ORBUtility.dprint(this, "nextState = " + nextState);
                    ORBUtility.dprint(this, "action    = " + action);
                    continue block0;
                }
                if (gr != Guard.Result.DEFERED) continue;
                deferral = true;
                continue block0;
            }
        } while (deferral);
        this.performStateTransition(fsm, in, nextState, action, debug);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void performStateTransition(FSM fsm, Input in, StateImpl nextState, Action action, boolean debug) {
        boolean different;
        block17: {
            StateImpl currentState = (StateImpl)fsm.getState();
            boolean bl = different = !currentState.equals(nextState);
            if (different) {
                if (debug) {
                    ORBUtility.dprint(this, "doIt: executing postAction for state " + currentState);
                }
                try {
                    currentState.postAction(fsm);
                }
                catch (Throwable thr) {
                    if (debug) {
                        ORBUtility.dprint(this, "doIt: postAction threw " + thr);
                    }
                    if (!(thr instanceof ThreadDeath)) break block17;
                    throw (ThreadDeath)thr;
                }
            }
        }
        try {
            if (action != null) {
                action.doIt(fsm, in);
            }
        }
        finally {
            if (different) {
                block19: {
                    if (debug) {
                        ORBUtility.dprint(this, "doIt: executing preAction for state " + nextState);
                    }
                    try {
                        nextState.preAction(fsm);
                    }
                    catch (Throwable thr) {
                        if (debug) {
                            ORBUtility.dprint(this, "doIt: preAction threw " + thr);
                        }
                        if (!(thr instanceof ThreadDeath)) break block19;
                        throw (ThreadDeath)thr;
                    }
                }
                ((FSMImpl)fsm).internalSetState(nextState);
            }
            if (debug) {
                ORBUtility.dprint(this, "doIt: state is now " + nextState);
            }
        }
    }

    @Override
    public FSM makeFSM(State startState) throws IllegalStateException {
        this.mustNotBeInitializing();
        return new FSMImpl(this, startState);
    }

    private void mustBeInitializing() throws IllegalStateException {
        if (!this.initializing) {
            throw new IllegalStateException("Invalid method call after initialization completed");
        }
    }

    private void mustNotBeInitializing() throws IllegalStateException {
        if (this.initializing) {
            throw new IllegalStateException("Invalid method call before initialization completed");
        }
    }
}

