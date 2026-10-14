/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Action;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.State;

public interface StateEngine {
    public StateEngine add(State var1, Input var2, Guard var3, Action var4, State var5) throws IllegalStateException;

    public StateEngine add(State var1, Input var2, Action var3, State var4) throws IllegalStateException;

    public StateEngine setDefault(State var1, Action var2, State var3) throws IllegalStateException;

    public StateEngine setDefault(State var1, State var2) throws IllegalStateException;

    public StateEngine setDefault(State var1) throws IllegalStateException;

    public void setDefaultAction(Action var1) throws IllegalStateException;

    public void done() throws IllegalStateException;

    public FSM makeFSM(State var1) throws IllegalStateException;
}

