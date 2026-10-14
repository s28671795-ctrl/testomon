/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class TypeDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public TypeCode type;

    public TypeDescription() {
    }

    public TypeDescription(String name, String id, String defined_in, String version, TypeCode type) {
        this.name = name;
        this.id = id;
        this.defined_in = defined_in;
        this.version = version;
        this.type = type;
    }
}

