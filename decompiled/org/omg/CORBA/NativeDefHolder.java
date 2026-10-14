/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.NativeDef;
import org.omg.CORBA.NativeDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class NativeDefHolder
implements Streamable {
    public NativeDef value;

    public NativeDefHolder() {
    }

    public NativeDefHolder(NativeDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NativeDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = NativeDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        NativeDefHelper.write(_out, this.value);
    }
}

