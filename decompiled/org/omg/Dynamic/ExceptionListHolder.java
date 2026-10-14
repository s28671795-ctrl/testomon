/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Dynamic;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Dynamic.ExceptionListHelper;

public final class ExceptionListHolder
implements Streamable {
    public TypeCode[] value;

    public ExceptionListHolder() {
    }

    public ExceptionListHolder(TypeCode[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExceptionListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExceptionListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExceptionListHelper.write(out, this.value);
    }
}

