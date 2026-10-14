/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TCKind;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyOperations;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

public interface DynUnionOperations
extends DynAnyOperations {
    public DynAny get_discriminator();

    public void set_discriminator(DynAny var1) throws TypeMismatch;

    public void set_to_default_member() throws TypeMismatch;

    public void set_to_no_active_member() throws TypeMismatch;

    public boolean has_no_active_member();

    public TCKind discriminator_kind();

    public DynAny member() throws InvalidValue;

    public String member_name() throws InvalidValue;

    public TCKind member_kind() throws InvalidValue;
}

