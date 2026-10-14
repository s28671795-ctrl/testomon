/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.DynamicAny.DynAnyOperations;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;

public interface DynEnumOperations
extends DynAnyOperations {
    public String get_as_string();

    public void set_as_string(String var1) throws InvalidValue;

    public int get_as_ulong();

    public void set_as_ulong(int var1) throws InvalidValue;
}

