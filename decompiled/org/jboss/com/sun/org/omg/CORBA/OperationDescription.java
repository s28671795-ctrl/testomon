/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.ExceptionDescription;
import org.jboss.com.sun.org.omg.CORBA.OperationMode;
import org.jboss.com.sun.org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class OperationDescription
implements IDLEntity {
    public String name = null;
    public String id = null;
    public String defined_in = null;
    public String version = null;
    public TypeCode result = null;
    public OperationMode mode = null;
    public String[] contexts = null;
    public ParameterDescription[] parameters = null;
    public ExceptionDescription[] exceptions = null;

    public OperationDescription() {
    }

    public OperationDescription(String _name, String _id, String _defined_in, String _version, TypeCode _result, OperationMode _mode, String[] _contexts, ParameterDescription[] _parameters, ExceptionDescription[] _exceptions) {
        this.name = _name;
        this.id = _id;
        this.defined_in = _defined_in;
        this.version = _version;
        this.result = _result;
        this.mode = _mode;
        this.contexts = _contexts;
        this.parameters = _parameters;
        this.exceptions = _exceptions;
    }
}

