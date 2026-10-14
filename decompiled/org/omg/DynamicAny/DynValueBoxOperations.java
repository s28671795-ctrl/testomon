/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
import org.omg.DynamicAny.DynValueCommonOperations;

public interface DynValueBoxOperations
extends DynValueCommonOperations {
    public Any get_boxed_value() throws InvalidValue;

    public void set_boxed_value(Any var1) throws InvalidValue, TypeMismatch;

    public DynAny get_boxed_value_as_dyn_any() throws InvalidValue;

    public void set_boxed_value_as_dyn_any(DynAny var1) throws TypeMismatch;
}

