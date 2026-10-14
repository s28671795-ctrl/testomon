/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class OperationDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public TypeCode result;
    public OperationMode mode;
    public String[] contexts;
    public ParameterDescription[] parameters;
    public ExceptionDescription[] exceptions;

    public OperationDescription() {
    }

    public OperationDescription(String name, String id, String defined_in, String version, TypeCode result, OperationMode mode, String[] contexts, ParameterDescription[] parameters, ExceptionDescription[] exceptions) {
        this.name = name;
        this.id = id;
        this.defined_in = defined_in;
        this.version = version;
        this.result = result;
        this.mode = mode;
        this.contexts = contexts;
        this.parameters = parameters;
        this.exceptions = exceptions;
    }
}

