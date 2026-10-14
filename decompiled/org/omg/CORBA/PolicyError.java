/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PolicyErrorHelper;
import org.omg.CORBA.UserException;

public final class PolicyError
extends UserException {
    private static final long serialVersionUID = 1L;
    public short reason;

    public PolicyError() {
        super(PolicyErrorHelper.id());
    }

    public PolicyError(String _reason, short reason) {
        super(_reason);
        this.reason = reason;
    }

    public PolicyError(short reason) {
        super(PolicyErrorHelper.id());
        this.reason = reason;
    }
}

