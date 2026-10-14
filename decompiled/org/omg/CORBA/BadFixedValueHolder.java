/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.BadFixedValue;
import org.omg.CORBA.BadFixedValueHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class BadFixedValueHolder
implements Streamable {
    public BadFixedValue value;

    public BadFixedValueHolder() {
    }

    public BadFixedValueHolder(BadFixedValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BadFixedValueHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = BadFixedValueHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        BadFixedValueHelper.write(_out, this.value);
    }
}

