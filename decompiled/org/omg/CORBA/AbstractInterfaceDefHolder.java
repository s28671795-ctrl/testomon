/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AbstractInterfaceDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AbstractInterfaceDefHolder
implements Streamable {
    public AbstractInterfaceDef value;

    public AbstractInterfaceDefHolder() {
    }

    public AbstractInterfaceDefHolder(AbstractInterfaceDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AbstractInterfaceDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AbstractInterfaceDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AbstractInterfaceDefHelper.write(_out, this.value);
    }
}

