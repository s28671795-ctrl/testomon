/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PrimitiveDef;
import org.omg.CORBA.PrimitiveDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PrimitiveDefHolder
implements Streamable {
    public PrimitiveDef value;

    public PrimitiveDefHolder() {
    }

    public PrimitiveDefHolder(PrimitiveDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PrimitiveDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PrimitiveDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PrimitiveDefHelper.write(_out, this.value);
    }
}

