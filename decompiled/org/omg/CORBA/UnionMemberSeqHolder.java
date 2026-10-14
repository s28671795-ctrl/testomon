/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.UnionMemberSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UnionMemberSeqHolder
implements Streamable {
    public UnionMember[] value;

    public UnionMemberSeqHolder() {
    }

    public UnionMemberSeqHolder(UnionMember[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UnionMemberSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = UnionMemberSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        UnionMemberSeqHelper.write(out, this.value);
    }
}

