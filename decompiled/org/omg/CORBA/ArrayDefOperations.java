/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeOperations;
import org.omg.CORBA.TypeCode;

public interface ArrayDefOperations
extends IDLTypeOperations {
    public int length();

    public void length(int var1);

    public TypeCode element_type();

    public IDLType element_type_def();

    public void element_type_def(IDLType var1);
}

