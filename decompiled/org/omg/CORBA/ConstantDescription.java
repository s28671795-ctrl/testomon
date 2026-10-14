/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class ConstantDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public TypeCode type;
    public Any value;

    public ConstantDescription() {
    }

    public ConstantDescription(String name, String id, String defined_in, String version, TypeCode type, Any value) {
        this.name = name;
        this.id = id;
        this.defined_in = defined_in;
        this.version = version;
        this.type = type;
        this.value = value;
    }
}

