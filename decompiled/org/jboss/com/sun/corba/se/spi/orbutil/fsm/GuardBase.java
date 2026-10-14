/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.fsm;

import org.jboss.com.sun.corba.se.impl.orbutil.fsm.NameBase;
import org.jboss.com.sun.corba.se.spi.orbutil.fsm.Guard;

public abstract class GuardBase
extends NameBase
implements Guard {
    public GuardBase(String name) {
        super(name);
    }
}

