/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.UserException;
import org.omg.PortableServer.POAPackage.InvalidPolicyHelper;

public final class InvalidPolicy
extends UserException {
    private static final long serialVersionUID = 1L;
    public short index;

    public InvalidPolicy() {
        super(InvalidPolicyHelper.id());
    }

    public InvalidPolicy(String _reason, short index) {
        super(_reason);
        this.index = index;
    }

    public InvalidPolicy(short index) {
        super(InvalidPolicyHelper.id());
        this.index = index;
    }
}

