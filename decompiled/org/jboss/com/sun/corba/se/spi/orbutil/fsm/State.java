/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import org.jboss.com.sun.corba.se.spi.orbutil.fsm.FSM;

public interface State {
    public void preAction(FSM var1);

    public void postAction(FSM var1);
}

