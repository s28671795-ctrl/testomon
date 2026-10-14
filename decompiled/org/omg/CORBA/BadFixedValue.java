/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.BadFixedValueHelper;
import org.omg.CORBA.UserException;

public final class BadFixedValue
extends UserException {
    private static final long serialVersionUID = 1L;
    public int offset;

    public BadFixedValue() {
        super(BadFixedValueHelper.id());
    }

    public BadFixedValue(String _reason, int offset) {
        super(_reason);
        this.offset = offset;
    }

    public BadFixedValue(int offset) {
        super(BadFixedValueHelper.id());
        this.offset = offset;
    }
}

