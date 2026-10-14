/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.ContainedOperations;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypeCode;

public interface ConstantDefOperations
extends ContainedOperations {
    public TypeCode type();

    public IDLType type_def();

    public void type_def(IDLType var1);

    public Any value();

    public void value(Any var1);
}

