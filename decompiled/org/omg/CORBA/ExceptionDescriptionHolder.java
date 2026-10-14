/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.ExceptionDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExceptionDescriptionHolder
implements Streamable {
    public ExceptionDescription value;

    public ExceptionDescriptionHolder() {
    }

    public ExceptionDescriptionHolder(ExceptionDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExceptionDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ExceptionDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExceptionDescriptionHelper.write(_out, this.value);
    }
}

