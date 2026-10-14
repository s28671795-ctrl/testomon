/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionDef;
import org.omg.CORBA.UnionDefHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UnionDefHolder
implements Streamable {
    public UnionDef value;

    public UnionDefHolder() {
    }

    public UnionDefHolder(UnionDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UnionDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = UnionDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        UnionDefHelper.write(_out, this.value);
    }
}

