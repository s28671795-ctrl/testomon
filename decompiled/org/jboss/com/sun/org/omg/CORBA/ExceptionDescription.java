/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class ExceptionDescription
implements IDLEntity {
    public String name = null;
    public String id = null;
    public String defined_in = null;
    public String version = null;
    public TypeCode type = null;

    public ExceptionDescription() {
    }

    public ExceptionDescription(String _name, String _id, String _defined_in, String _version, TypeCode _type) {
        this.name = _name;
        this.id = _id;
        this.defined_in = _defined_in;
        this.version = _version;
        this.type = _type;
    }
}

