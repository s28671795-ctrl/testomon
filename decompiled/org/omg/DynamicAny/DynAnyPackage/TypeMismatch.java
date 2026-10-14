/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyPackage;

import org.omg.CORBA.UserException;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatchHelper;

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

