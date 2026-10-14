/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.NoServantHelper;

public final class NoServant
extends UserException {
    private static final long serialVersionUID = 1L;

    public NoServant() {
        super(NoServantHelper.id());
    }

    public NoServant(String value) {
        super(value);
    }
}

