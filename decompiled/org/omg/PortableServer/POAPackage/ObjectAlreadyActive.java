/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper;

public final class ObjectAlreadyActive
extends UserException {
    private static final long serialVersionUID = 1L;

    public ObjectAlreadyActive() {
        super(ObjectAlreadyActiveHelper.id());
    }

    public ObjectAlreadyActive(String value) {
        super(value);
    }
}

