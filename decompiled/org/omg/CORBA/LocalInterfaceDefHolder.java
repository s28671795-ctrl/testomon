/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.LocalInterfaceDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class LocalInterfaceDefHolder
implements Streamable {
    public LocalInterfaceDef value;

    public LocalInterfaceDefHolder() {
    }

    public LocalInterfaceDefHolder(LocalInterfaceDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocalInterfaceDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LocalInterfaceDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        LocalInterfaceDefHelper.write(_out, this.value);
    }
}

