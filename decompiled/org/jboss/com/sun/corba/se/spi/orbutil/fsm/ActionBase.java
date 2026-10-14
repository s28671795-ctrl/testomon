/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import org.jboss.com.sun.corba.se.impl.orbutil.fsm.NameBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Action;

public abstract class ActionBase
extends NameBase
implements Action {
    public ActionBase(String name) {
        super(name);
    }
}

