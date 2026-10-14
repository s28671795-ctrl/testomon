/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Dynamic;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Dynamic.Parameter;
import org.omg.Dynamic.ParameterHelper;

public final class ParameterHolder
implements Streamable {
    public Parameter value;

    public ParameterHolder() {
    }

    public ParameterHolder(Parameter initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ParameterHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ParameterHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ParameterHelper.write(_out, this.value);
    }
}

