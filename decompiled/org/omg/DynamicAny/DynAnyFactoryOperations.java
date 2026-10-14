/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
import org.omg.DynamicAny.MustTruncate;

public interface DynAnyFactoryOperations {
    public DynAny create_dyn_any(Any var1) throws InconsistentTypeCode;

    public DynAny create_dyn_any_from_type_code(TypeCode var1) throws InconsistentTypeCode;

    public DynAny create_dyn_any_without_truncation(Any var1) throws MustTruncate, InconsistentTypeCode;

    public DynAny[] create_multiple_dyn_anys(Any[] var1, boolean var2) throws MustTruncate, InconsistentTypeCode;

    public Any[] create_multiple_anys(DynAny[] var1);
}

