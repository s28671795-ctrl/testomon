/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtAbstractInterfaceDef;
import org.omg.CORBA.ExtAbstractInterfaceDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtAbstractInterfaceDefHolder
implements Streamable {
    public ExtAbstractInterfaceDef value;

    public ExtAbstractInterfaceDefHolder() {
    }

    public ExtAbstractInterfaceDefHolder(ExtAbstractInterfaceDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtAbstractInterfaceDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtAbstractInterfaceDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtAbstractInterfaceDefHelper.write(_out, this.value);
    }
}

