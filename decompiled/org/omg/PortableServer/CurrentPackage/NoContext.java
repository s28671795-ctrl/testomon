/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.CurrentPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.CurrentPackage.NoContextHelper;

public final class NoContext
extends UserException {
    private static final long serialVersionUID = 1L;

    public NoContext() {
        super(NoContextHelper.id());
    }

    public NoContext(String value) {
        super(value);
    }
}

