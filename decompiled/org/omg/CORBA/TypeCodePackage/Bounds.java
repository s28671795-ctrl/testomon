/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.TypeCodePackage;

import org.omg.CORBA.TypeCodePackage.BoundsHelper;
import org.omg.CORBA.UserException;

public final class Bounds
extends UserException {
    private static final long serialVersionUID = 1L;

    public Bounds() {
        super(BoundsHelper.id());
    }

    public Bounds(String value) {
        super(value);
    }
}

