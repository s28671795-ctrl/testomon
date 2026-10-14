/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.ParameterDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ParameterDescriptionHolder
implements Streamable {
    public ParameterDescription value;

    public ParameterDescriptionHolder() {
    }

    public ParameterDescriptionHolder(ParameterDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ParameterDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ParameterDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ParameterDescriptionHelper.write(_out, this.value);
    }
}

