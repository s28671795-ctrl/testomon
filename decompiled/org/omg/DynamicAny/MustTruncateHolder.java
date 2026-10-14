/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.MustTruncate;
import org.omg.DynamicAny.MustTruncateHelper;

public final class MustTruncateHolder
implements Streamable {
    public MustTruncate value;

    public MustTruncateHolder() {
    }

    public MustTruncateHolder(MustTruncate initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MustTruncateHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = MustTruncateHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        MustTruncateHelper.write(_out, this.value);
    }
}

