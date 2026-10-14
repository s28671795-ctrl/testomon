/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.BoundsHelper;
import org.omg.CORBA.UserException;

public final class Bounds
extends UserException {
    public Bounds() {
        super(BoundsHelper.id());
    }

    public Bounds(String reason_str) {
        super(BoundsHelper.id() + " " + reason_str);
    }
}

