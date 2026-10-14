/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class StructMember
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public TypeCode type;
    public IDLType type_def;

    public StructMember() {
    }

    public StructMember(String name, TypeCode type, IDLType type_def) {
        this.name = name;
        this.type = type;
        this.type_def = type_def;
    }
}

