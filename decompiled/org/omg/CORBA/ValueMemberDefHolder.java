/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMemberDef;
import org.omg.CORBA.ValueMemberDefHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueMemberDefHolder
implements Streamable {
    public ValueMemberDef value;

    public ValueMemberDefHolder() {
    }

    public ValueMemberDefHolder(ValueMemberDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueMemberDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ValueMemberDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ValueMemberDefHelper.write(_out, this.value);
    }
}

