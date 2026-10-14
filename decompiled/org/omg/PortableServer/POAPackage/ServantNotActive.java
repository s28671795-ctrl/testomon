/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.ServantNotActiveHelper;

public final class ServantNotActive
extends UserException {
    private static final long serialVersionUID = 1L;

    public ServantNotActive() {
        super(ServantNotActiveHelper.id());
    }

    public ServantNotActive(String value) {
        super(value);
    }
}

