/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class ValueMember
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public TypeCode type;
    public IDLType type_def;
    public short access;

    public ValueMember() {
    }

    public ValueMember(String name, String id, String defined_in, String version, TypeCode type, IDLType type_def, short access) {
        this.name = name;
        this.id = id;
        this.defined_in = defined_in;
        this.version = version;
        this.type = type;
        this.type_def = type_def;
        this.access = access;
    }
}

