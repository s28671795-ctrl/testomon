/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.ObjectNotActiveHelper;

public final class ObjectNotActive
extends UserException {
    private static final long serialVersionUID = 1L;

    public ObjectNotActive() {
        super(ObjectNotActiveHelper.id());
    }

    public ObjectNotActive(String value) {
        super(value);
    }
}

