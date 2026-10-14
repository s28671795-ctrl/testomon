/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.DynamicAny.DynAnyOperations;

public interface DynValueCommonOperations
extends DynAnyOperations {
    public boolean is_null();

    public void set_to_null();

    public void set_to_value();
}

