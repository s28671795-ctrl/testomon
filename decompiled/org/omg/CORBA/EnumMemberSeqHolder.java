/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.EnumMemberSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class EnumMemberSeqHolder
implements Streamable {
    public String[] value;

    public EnumMemberSeqHolder() {
    }

    public EnumMemberSeqHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return EnumMemberSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = EnumMemberSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        EnumMemberSeqHelper.write(out, this.value);
    }
}

