/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExistsHelper;

public final class AdapterAlreadyExists
extends UserException {
    private static final long serialVersionUID = 1L;

    public AdapterAlreadyExists() {
        super(AdapterAlreadyExistsHelper.id());
    }

    public AdapterAlreadyExists(String value) {
        super(value);
    }
}

