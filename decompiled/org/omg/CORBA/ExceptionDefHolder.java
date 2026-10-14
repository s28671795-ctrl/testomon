/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExceptionDefHolder
implements Streamable {
    public ExceptionDef value;

    public ExceptionDefHolder() {
    }

    public ExceptionDefHolder(ExceptionDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExceptionDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExceptionDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExceptionDefHelper.write(_out, this.value);
    }
}

