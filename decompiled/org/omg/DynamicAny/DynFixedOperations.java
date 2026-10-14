/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.DynamicAny.DynAnyOperations;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

public interface DynFixedOperations
extends DynAnyOperations {
    public String get_value();

    public boolean set_value(String var1) throws InvalidValue, TypeMismatch;
}

