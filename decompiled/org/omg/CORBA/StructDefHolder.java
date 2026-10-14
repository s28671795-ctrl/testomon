/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.StructDef;
import org.omg.CORBA.StructDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class StructDefHolder
implements Streamable {
    public StructDef value;

    public StructDefHolder() {
    }

    public StructDefHolder(StructDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return StructDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = StructDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        StructDefHelper.write(_out, this.value);
    }
}

