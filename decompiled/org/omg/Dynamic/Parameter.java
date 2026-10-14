/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Dynamic;

import org.omg.CORBA.Any;
import org.omg.CORBA.ParameterMode;
import org.omg.CORBA.portable.IDLEntity;

public final class Parameter
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public Any argument;
    public ParameterMode mode;

    public Parameter() {
    }

    public Parameter(Any argument, ParameterMode mode) {
        this.argument = argument;
        this.mode = mode;
    }
}

