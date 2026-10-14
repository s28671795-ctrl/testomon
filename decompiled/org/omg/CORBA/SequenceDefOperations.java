/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeOperations;
import org.omg.CORBA.TypeCode;

public interface SequenceDefOperations
extends IDLTypeOperations {
    public int bound();

    public void bound(int var1);

    public TypeCode element_type();

    public IDLType element_type_def();

    public void element_type_def(IDLType var1);
}

