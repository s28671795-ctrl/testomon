/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecPackage;

import org.omg.CORBA.UserException;
import org.omg.IOP.CodecPackage.FormatMismatchHelper;

public final class FormatMismatch
extends UserException {
    private static final long serialVersionUID = 1L;

    public FormatMismatch() {
        super(FormatMismatchHelper.id());
    }

    public FormatMismatch(String value) {
        super(value);
    }
}

