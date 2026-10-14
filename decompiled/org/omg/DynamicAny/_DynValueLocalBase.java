/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.LocalObject;
import org.omg.DynamicAny.DynValue;

public abstract class _DynValueLocalBase
extends LocalObject
implements DynValue {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/DynamicAny/DynValue:1.0", "IDL:omg.org/DynamicAny/DynValueCommon:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

