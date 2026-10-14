/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExceptionDefSeqHolder
implements Streamable {
    public ExceptionDef[] value;

    public ExceptionDefSeqHolder() {
    }

    public ExceptionDefSeqHolder(ExceptionDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExceptionDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExceptionDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExceptionDefSeqHelper.write(out, this.value);
    }
}

