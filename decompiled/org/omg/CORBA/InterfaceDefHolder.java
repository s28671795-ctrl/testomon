/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InterfaceDefHolder
implements Streamable {
    public InterfaceDef value;

    public InterfaceDefHolder() {
    }

    public InterfaceDefHolder(InterfaceDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InterfaceDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = InterfaceDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        InterfaceDefHelper.write(_out, this.value);
    }
}

