/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InvalidPoliciesHelper;
import org.omg.CORBA.UserException;

public final class InvalidPolicies
extends UserException {
    private static final long serialVersionUID = 1L;
    public short[] indicies;

    public InvalidPolicies() {
        super(InvalidPoliciesHelper.id());
    }

    public InvalidPolicies(String _reason, short[] indicies) {
        super(_reason);
        this.indicies = indicies;
    }

    public InvalidPolicies(short[] indicies) {
        super(InvalidPoliciesHelper.id());
        this.indicies = indicies;
    }
}

