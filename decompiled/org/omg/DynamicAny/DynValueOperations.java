/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TCKind;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
import org.omg.DynamicAny.DynValueCommonOperations;
import org.omg.DynamicAny.NameDynAnyPair;
import org.omg.DynamicAny.NameValuePair;

public interface DynValueOperations
extends DynValueCommonOperations {
    public String current_member_name() throws InvalidValue, TypeMismatch;

    public TCKind current_member_kind() throws InvalidValue, TypeMismatch;

    public NameValuePair[] get_members() throws InvalidValue;

    public void set_members(NameValuePair[] var1) throws InvalidValue, TypeMismatch;

    public NameDynAnyPair[] get_members_as_dyn_any() throws InvalidValue;

    public void set_members_as_dyn_any(NameDynAnyPair[] var1) throws InvalidValue, TypeMismatch;
}

