/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.ConstantDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ConstantDefHolder
implements Streamable {
    public ConstantDef value;

    public ConstantDefHolder() {
    }

    public ConstantDefHolder(ConstantDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ConstantDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ConstantDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ConstantDefHelper.write(_out, this.value);
    }
}

