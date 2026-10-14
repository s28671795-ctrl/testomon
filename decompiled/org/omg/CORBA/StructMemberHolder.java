/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class StructMemberHolder
implements Streamable {
    public StructMember value;

    public StructMemberHolder() {
    }

    public StructMemberHolder(StructMember initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return StructMemberHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = StructMemberHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        StructMemberHelper.write(_out, this.value);
    }
}

