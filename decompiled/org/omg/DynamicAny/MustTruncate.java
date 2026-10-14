/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.UserException;
import org.omg.DynamicAny.MustTruncateHelper;

public final class MustTruncate
extends UserException {
    private static final long serialVersionUID = 1L;

    public MustTruncate() {
        super(MustTruncateHelper.id());
    }

    public MustTruncate(String value) {
        super(value);
    }
}

