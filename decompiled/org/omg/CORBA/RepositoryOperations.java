/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ArrayDef;
import org.omg.CORBA.Contained;
import org.omg.CORBA.ContainerOperations;
import org.omg.CORBA.FixedDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.PrimitiveDef;
import org.omg.CORBA.PrimitiveKind;
import org.omg.CORBA.SequenceDef;
import org.omg.CORBA.StringDef;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WstringDef;

public interface RepositoryOperations
extends ContainerOperations {
    public Contained lookup_id(String var1);

    public TypeCode get_canonical_typecode(TypeCode var1);

    public PrimitiveDef get_primitive(PrimitiveKind var1);

    public StringDef create_string(int var1);

    public WstringDef create_wstring(int var1);

    public SequenceDef create_sequence(int var1, IDLType var2);

    public ArrayDef create_array(int var1, IDLType var2);

    public FixedDef create_fixed(short var1, short var2);
}

