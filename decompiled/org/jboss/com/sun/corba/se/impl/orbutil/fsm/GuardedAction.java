/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.fsm;

import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Action;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.GuardBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.State;

public class GuardedAction {
    private static Guard trueGuard = new GuardBase("true"){

        @Override
        public Guard.Result evaluate(FSM fsm, Input in) {
            return Guard.Result.ENABLED;
        }
    };
    private Guard guard;
    private Action action;
    private State nextState;

    public GuardedAction(Action action, State nextState) {
        this.guard = trueGuard;
        this.action = action;
        this.nextState = nextState;
    }

    public GuardedAction(Guard guard, Action action, State nextState) {
        this.guard = guard;
        this.action = action;
        this.nextState = nextState;
    }

    public String toString() {
        return "GuardedAction[action=" + this.action + " guard=" + this.guard + " nextState=" + this.nextState + "]";
    }

    public Action getAction() {
        return this.action;
    }

    public Guard getGuard() {
        return this.guard;
    }

    public State getNextState() {
        return this.nextState;
    }
}

