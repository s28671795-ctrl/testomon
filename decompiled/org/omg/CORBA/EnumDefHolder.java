/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.EnumDef;
import org.omg.CORBA.EnumDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class EnumDefHolder
implements Streamable {
    public EnumDef value;

    public EnumDefHolder() {
    }

    public EnumDefHolder(EnumDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return EnumDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = EnumDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        EnumDefHelper.write(_out, this.value);
    }
}

