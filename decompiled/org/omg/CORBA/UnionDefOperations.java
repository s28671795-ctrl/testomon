/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainerOperations;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypedefDefOperations;
import org.omg.CORBA.UnionMember;

public interface UnionDefOperations
extends TypedefDefOperations,
ContainerOperations {
    public TypeCode discriminator_type();

    public IDLType discriminator_type_def();

    public void discriminator_type_def(IDLType var1);

    public UnionMember[] members();

    public void members(UnionMember[] var1);
}

