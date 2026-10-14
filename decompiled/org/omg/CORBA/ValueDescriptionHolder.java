/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDescription;
import org.omg.CORBA.ValueDescriptionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueDescriptionHolder
implements Streamable {
    public ValueDescription value;

    public ValueDescriptionHolder() {
    }

    public ValueDescriptionHolder(ValueDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ValueDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ValueDescriptionHelper.write(_out, this.value);
    }
}

