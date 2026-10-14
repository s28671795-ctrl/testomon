/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.ValueMemberHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueMemberHolder
implements Streamable {
    public ValueMember value;

    public ValueMemberHolder() {
    }

    public ValueMemberHolder(ValueMember initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueMemberHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ValueMemberHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ValueMemberHelper.write(_out, this.value);
    }
}

