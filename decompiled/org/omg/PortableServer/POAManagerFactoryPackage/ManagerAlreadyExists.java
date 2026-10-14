/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerFactoryPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAManagerFactoryPackage.ManagerAlreadyExistsHelper;

public final class ManagerAlreadyExists
extends UserException {
    private static final long serialVersionUID = 1L;

    public ManagerAlreadyExists() {
        super(ManagerAlreadyExistsHelper.id());
    }

    public ManagerAlreadyExists(String value) {
        super(value);
    }
}

