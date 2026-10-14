/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypedefDefOperations;

public interface AliasDefOperations
extends TypedefDefOperations {
    public IDLType original_type_def();

    public void original_type_def(IDLType var1);
}

