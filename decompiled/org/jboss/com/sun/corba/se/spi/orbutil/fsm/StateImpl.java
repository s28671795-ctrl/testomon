/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jboss.com.sun.corba.se.impl.orbutil.fsm.GuardedAction;
import org.jboss.com.sun.corba.se.impl.orbutil.fsm.NameBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Action;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.State;

public class StateImpl
extends NameBase
implements State {
    private Action defaultAction = null;
    private State defaultNextState;
    private Map<Input, Set<GuardedAction>> inputToGuardedActions = new HashMap<Input, Set<GuardedAction>>();

    public StateImpl(String name) {
        super(name);
    }

    @Override
    public void preAction(FSM fsm) {
    }

    @Override
    public void postAction(FSM fsm) {
    }

    public State getDefaultNextState() {
        return this.defaultNextState;
    }

    public void setDefaultNextState(State defaultNextState) {
        this.defaultNextState = defaultNextState;
    }

    public Action getDefaultAction() {
        return this.defaultAction;
    }

    public void setDefaultAction(Action defaultAction) {
        this.defaultAction = defaultAction;
    }

    public void addGuardedAction(Input in, GuardedAction ga) {
        Set<GuardedAction> gas = this.inputToGuardedActions.get(in);
        if (gas == null) {
            gas = new HashSet<GuardedAction>();
            this.inputToGuardedActions.put(in, gas);
        }
        gas.add(ga);
    }

    public Set<GuardedAction> getGuardedActions(Input in) {
        return this.inputToGuardedActions.get(in);
    }
}

