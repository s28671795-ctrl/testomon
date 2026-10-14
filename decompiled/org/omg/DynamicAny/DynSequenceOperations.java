/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyOperations;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

public interface DynSequenceOperations
extends DynAnyOperations {
    public int get_length();

    public void set_length(int var1) throws InvalidValue;

    public Any[] get_elements();

    public void set_elements(Any[] var1) throws InvalidValue, TypeMismatch;

    public DynAny[] get_elements_as_dyn_any();

    public void set_elements_as_dyn_any(DynAny[] var1) throws InvalidValue, TypeMismatch;
}

