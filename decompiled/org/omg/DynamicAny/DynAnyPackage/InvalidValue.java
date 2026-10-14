/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyPackage;

import org.omg.CORBA.UserException;
import org.omg.DynamicAny.DynAnyPackage.InvalidValueHelper;

public final class InvalidValue
extends UserException {
    private static final long serialVersionUID = 1L;

    public InvalidValue() {
        super(InvalidValueHelper.id());
    }

    public InvalidValue(String value) {
        super(value);
    }
}

