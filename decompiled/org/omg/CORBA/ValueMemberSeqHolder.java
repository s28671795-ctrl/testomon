/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.ValueMemberSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueMemberSeqHolder
implements Streamable {
    public ValueMember[] value;

    public ValueMemberSeqHolder() {
    }

    public ValueMemberSeqHolder(ValueMember[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueMemberSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ValueMemberSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ValueMemberSeqHelper.write(out, this.value);
    }
}

