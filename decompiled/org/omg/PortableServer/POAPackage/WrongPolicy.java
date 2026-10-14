/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.WrongPolicyHelper;

public final class WrongPolicy
extends UserException {
    private static final long serialVersionUID = 1L;

    public WrongPolicy() {
        super(WrongPolicyHelper.id());
    }

    public WrongPolicy(String value) {
        super(value);
    }
}

