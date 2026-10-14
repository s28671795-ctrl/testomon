/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.StringValueHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class StringValueHolder
implements Streamable {
    public String value;

    public StringValueHolder() {
    }

    public StringValueHolder(String initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return StringValueHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = StringValueHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        StringValueHelper.write(_out, this.value);
    }
}

