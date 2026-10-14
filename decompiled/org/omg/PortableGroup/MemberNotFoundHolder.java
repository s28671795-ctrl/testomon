/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.MemberNotFound;
import org.omg.PortableGroup.MemberNotFoundHelper;

public final class MemberNotFoundHolder
implements Streamable {
    public MemberNotFound value;

    public MemberNotFoundHolder() {
    }

    public MemberNotFoundHolder(MemberNotFound initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MemberNotFoundHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = MemberNotFoundHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        MemberNotFoundHelper.write(_out, this.value);
    }
}

