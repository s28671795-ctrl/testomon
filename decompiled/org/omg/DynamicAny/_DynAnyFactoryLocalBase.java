/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.LocalObject;
import org.omg.DynamicAny.DynAnyFactory;

public abstract class _DynAnyFactoryLocalBase
extends LocalObject
implements DynAnyFactory {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/DynamicAny/DynAnyFactory:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

