/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IDLType;
import org.omg.CORBA.ParameterMode;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class ParameterDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public TypeCode type;
    public IDLType type_def;
    public ParameterMode mode;

    public ParameterDescription() {
    }

    public ParameterDescription(String name, TypeCode type, IDLType type_def, ParameterMode mode) {
        this.name = name;
        this.type = type;
        this.type_def = type_def;
        this.mode = mode;
    }
}

