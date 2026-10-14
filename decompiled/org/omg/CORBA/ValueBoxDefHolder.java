/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueBoxDef;
import org.omg.CORBA.ValueBoxDefHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueBoxDefHolder
implements Streamable {
    public ValueBoxDef value;

    public ValueBoxDefHolder() {
    }

    public ValueBoxDefHolder(ValueBoxDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueBoxDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ValueBoxDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ValueBoxDefHelper.write(_out, this.value);
    }
}

