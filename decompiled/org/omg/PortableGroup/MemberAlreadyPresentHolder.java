/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.MemberAlreadyPresent;
import org.omg.PortableGroup.MemberAlreadyPresentHelper;

public final class MemberAlreadyPresentHolder
implements Streamable {
    public MemberAlreadyPresent value;

    public MemberAlreadyPresentHolder() {
    }

    public MemberAlreadyPresentHolder(MemberAlreadyPresent initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MemberAlreadyPresentHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = MemberAlreadyPresentHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        MemberAlreadyPresentHelper.write(_out, this.value);
    }
}

