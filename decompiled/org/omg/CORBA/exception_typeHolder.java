/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.exception_type;
import org.omg.CORBA.exception_typeHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class exception_typeHolder
implements Streamable {
    public exception_type value;

    public exception_typeHolder() {
    }

    public exception_typeHolder(exception_type initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return exception_typeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = exception_typeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        exception_typeHelper.write(out, this.value);
    }
}

