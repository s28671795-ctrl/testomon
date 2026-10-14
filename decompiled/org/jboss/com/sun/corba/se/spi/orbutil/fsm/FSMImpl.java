/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.fsm.StateEngineImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.State;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.StateEngine;

public class FSMImpl
implements FSM {
    private boolean debug;
    private State state;
    private StateEngineImpl stateEngine;

    public FSMImpl(StateEngine se, State startState) {
        this(se, startState, false);
    }

    public FSMImpl(StateEngine se, State startState, boolean debug) {
        this.state = startState;
        this.stateEngine = (StateEngineImpl)se;
        this.debug = debug;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void doIt(Input in) {
        this.stateEngine.doIt(this, in, this.debug);
    }

    public void internalSetState(State nextState) {
        if (this.debug) {
            ORBUtility.dprint(this, "Calling internalSetState with nextState = " + nextState);
        }
        this.state = nextState;
        if (this.debug) {
            ORBUtility.dprint(this, "Exiting internalSetState with state = " + this.state);
        }
    }
}

