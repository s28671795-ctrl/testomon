/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;

public interface TypeCodeOperations {
    public boolean equal(TypeCode var1);

    public boolean equivalent(TypeCode var1);

    public TypeCode get_compact_typecode();

    public TCKind kind();

    public String id() throws BadKind;

    public String name() throws BadKind;

    public int member_count() throws BadKind;

    public String member_name(int var1) throws Bounds, BadKind;

    public TypeCode member_type(int var1) throws Bounds, BadKind;

    public Any member_label(int var1) throws Bounds, BadKind;

    public TypeCode discriminator_type() throws BadKind;

    public int default_index() throws BadKind;

    public int length() throws BadKind;

    public TypeCode content_type() throws BadKind;

    public short fixed_digits() throws BadKind;

    public short fixed_scale() throws BadKind;

    public short member_visibility(int var1) throws Bounds, BadKind;

    public short type_modifier() throws BadKind;

    public TypeCode concrete_base_type() throws BadKind;
}

