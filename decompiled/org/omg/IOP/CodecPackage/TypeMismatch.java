/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecPackage;

import org.omg.CORBA.UserException;
import org.omg.IOP.CodecPackage.TypeMismatchHelper;

public final class TypeMismatch
extends UserException {
    private static final long serialVersionUID = 1L;

    public TypeMismatch() {
        super(TypeMismatchHelper.id());
    }

    public TypeMismatch(String value) {
        super(value);
    }
}

