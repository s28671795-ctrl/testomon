/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContainedOperations;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypeCode;

public interface ValueMemberDefOperations
extends ContainedOperations {
    public TypeCode type();

    public IDLType type_def();

    public void type_def(IDLType var1);

    public short access();

    public void access(short var1);
}

