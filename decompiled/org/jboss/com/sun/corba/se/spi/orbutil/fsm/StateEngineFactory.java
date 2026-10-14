/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import org.jboss.com.sun.corba.se.impl.orbutil.fsm.StateEngineImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.StateEngine;

public class StateEngineFactory {
    private StateEngineFactory() {
    }

    public static StateEngine create() {
        return new StateEngineImpl();
    }
}

