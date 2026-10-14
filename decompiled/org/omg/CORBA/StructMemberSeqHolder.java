/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class StructMemberSeqHolder
implements Streamable {
    public StructMember[] value;

    public StructMemberSeqHolder() {
    }

    public StructMemberSeqHolder(StructMember[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return StructMemberSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = StructMemberSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        StructMemberSeqHelper.write(out, this.value);
    }
}

