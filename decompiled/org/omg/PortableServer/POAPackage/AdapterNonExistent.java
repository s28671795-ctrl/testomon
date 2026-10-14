/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.AdapterNonExistentHelper;

public final class AdapterNonExistent
extends UserException {
    private static final long serialVersionUID = 1L;

    public AdapterNonExistent() {
        super(AdapterNonExistentHelper.id());
    }

    public AdapterNonExistent(String value) {
        super(value);
    }
}

