/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyFactoryOperations;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
import org.omg.DynamicAny.MustTruncate;
import org.omg.DynamicAny._DynAnyFactoryLocalBase;

public class DynAnyFactoryLocalTie
extends _DynAnyFactoryLocalBase {
    private static final long serialVersionUID = 1L;
    private DynAnyFactoryOperations _delegate;

    public DynAnyFactoryLocalTie(DynAnyFactoryOperations delegate) {
        this._delegate = delegate;
    }

    public DynAnyFactoryOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(DynAnyFactoryOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public DynAny create_dyn_any_without_truncation(Any value) throws MustTruncate, InconsistentTypeCode {
        return this._delegate.create_dyn_any_without_truncation(value);
    }

    @Override
    public Any[] create_multiple_anys(DynAny[] values2) {
        return this._delegate.create_multiple_anys(values2);
    }

    @Override
    public DynAny create_dyn_any(Any value) throws InconsistentTypeCode {
        return this._delegate.create_dyn_any(value);
    }

    @Override
    public DynAny[] create_multiple_dyn_anys(Any[] values2, boolean allow_truncate) throws MustTruncate, InconsistentTypeCode {
        return this._delegate.create_multiple_dyn_anys(values2, allow_truncate);
    }

    @Override
    public DynAny create_dyn_any_from_type_code(TypeCode type) throws InconsistentTypeCode {
        return this._delegate.create_dyn_any_from_type_code(type);
    }
}

