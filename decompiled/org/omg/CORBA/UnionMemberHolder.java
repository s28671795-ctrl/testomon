/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.UnionMemberHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UnionMemberHolder
implements Streamable {
    public UnionMember value;

    public UnionMemberHolder() {
    }

    public UnionMemberHolder(UnionMember initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UnionMemberHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = UnionMemberHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        UnionMemberHelper.write(_out, this.value);
    }
}

