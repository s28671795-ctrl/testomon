/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.ServantAlreadyActiveHelper;

public final class ServantAlreadyActive
extends UserException {
    private static final long serialVersionUID = 1L;

    public ServantAlreadyActive() {
        super(ServantAlreadyActiveHelper.id());
    }

    public ServantAlreadyActive(String value) {
        super(value);
    }
}

