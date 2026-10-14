/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainerOperations;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypedefDefOperations;

public interface StructDefOperations
extends TypedefDefOperations,
ContainerOperations {
    public StructMember[] members();

    public void members(StructMember[] var1);
}

