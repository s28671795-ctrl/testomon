/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ParameterMode;
import org.omg.CORBA.ParameterModeHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ParameterModeHolder
implements Streamable {
    public ParameterMode value;

    public ParameterModeHolder() {
    }

    public ParameterModeHolder(ParameterMode initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ParameterModeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ParameterModeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ParameterModeHelper.write(out, this.value);
    }
}

