/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.FixedDef;
import org.omg.CORBA.FixedDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class FixedDefHolder
implements Streamable {
    public FixedDef value;

    public FixedDefHolder() {
    }

    public FixedDefHolder(FixedDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FixedDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = FixedDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        FixedDefHelper.write(_out, this.value);
    }
}

