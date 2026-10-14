/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class ExtAttributeDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public TypeCode type;
    public AttributeMode mode;
    public ExceptionDescription[] get_exceptions;
    public ExceptionDescription[] put_exceptions;

    public ExtAttributeDescription() {
    }

    public ExtAttributeDescription(String name, String id, String defined_in, String version, TypeCode type, AttributeMode mode, ExceptionDescription[] get_exceptions, ExceptionDescription[] put_exceptions) {
        this.name = name;
        this.id = id;
        this.defined_in = defined_in;
        this.version = version;
        this.type = type;
        this.mode = mode;
        this.get_exceptions = get_exceptions;
        this.put_exceptions = put_exceptions;
    }
}

