/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.DynamicAny.DynAny;

public final class NameDynAnyPair
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String id = "";
    public DynAny value;

    public NameDynAnyPair() {
    }

    public NameDynAnyPair(String id, DynAny value) {
        this.id = id;
        this.value = value;
    }
}

