/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainedOperations;
import org.omg.CORBA.ContainerOperations;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;

public interface ExceptionDefOperations
extends ContainedOperations,
ContainerOperations {
    public TypeCode type();

    public StructMember[] members();

    public void members(StructMember[] var1);
}

