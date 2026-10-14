/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa;

import org.jboss.com.sun.corba.se.spi.oa.NullServant;
import org.omg.CORBA.SystemException;

public class NullServantImpl
implements NullServant {
    private SystemException sysex;

    public NullServantImpl(SystemException ex) {
        this.sysex = ex;
    }

    @Override
    public SystemException getException() {
        return this.sysex;
    }
}

