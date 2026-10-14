/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDef;
import org.omg.CORBA.ValueDefHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueDefHolder
implements Streamable {
    public ValueDef value;

    public ValueDefHolder() {
    }

    public ValueDefHolder(ValueDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ValueDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ValueDefHelper.write(_out, this.value);
    }
}

