/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtLocalInterfaceDef;
import org.omg.CORBA.ExtLocalInterfaceDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtLocalInterfaceDefHolder
implements Streamable {
    public ExtLocalInterfaceDef value;

    public ExtLocalInterfaceDefHolder() {
    }

    public ExtLocalInterfaceDefHolder(ExtLocalInterfaceDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtLocalInterfaceDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtLocalInterfaceDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtLocalInterfaceDefHelper.write(_out, this.value);
    }
}

