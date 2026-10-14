/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Dynamic;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Dynamic.Parameter;
import org.omg.Dynamic.ParameterListHelper;

public final class ParameterListHolder
implements Streamable {
    public Parameter[] value;

    public ParameterListHolder() {
    }

    public ParameterListHolder(Parameter[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ParameterListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ParameterListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ParameterListHelper.write(out, this.value);
    }
}

