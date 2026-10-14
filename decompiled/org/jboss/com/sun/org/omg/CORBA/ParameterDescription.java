/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.ParameterMode;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class ParameterDescription
implements IDLEntity {
    public String name = null;
    public TypeCode type = null;
    public IDLType type_def = null;
    public ParameterMode mode = null;

    public ParameterDescription() {
    }

    public ParameterDescription(String _name, TypeCode _type, IDLType _type_def, ParameterMode _mode) {
        this.name = _name;
        this.type = _type;
        this.type_def = _type_def;
        this.mode = _mode;
    }
}

