/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtInterfaceDef;
import org.omg.CORBA.ExtInterfaceDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtInterfaceDefHolder
implements Streamable {
    public ExtInterfaceDef value;

    public ExtInterfaceDefHolder() {
    }

    public ExtInterfaceDefHolder(ExtInterfaceDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtInterfaceDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtInterfaceDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtInterfaceDefHelper.write(_out, this.value);
    }
}

