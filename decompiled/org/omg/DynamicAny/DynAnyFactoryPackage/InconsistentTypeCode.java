/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyFactoryPackage;

import org.omg.CORBA.UserException;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCodeHelper;

public final class InconsistentTypeCode
extends UserException {
    private static final long serialVersionUID = 1L;

    public InconsistentTypeCode() {
        super(InconsistentTypeCodeHelper.id());
    }

    public InconsistentTypeCode(String value) {
        super(value);
    }
}

