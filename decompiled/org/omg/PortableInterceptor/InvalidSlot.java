/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.UserException;
import org.omg.PortableInterceptor.InvalidSlotHelper;

public final class InvalidSlot
extends UserException {
    private static final long serialVersionUID = 1L;

    public InvalidSlot() {
        super(InvalidSlotHelper.id());
    }

    public InvalidSlot(String value) {
        super(value);
    }
}

