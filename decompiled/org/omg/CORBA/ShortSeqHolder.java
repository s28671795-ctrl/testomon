/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ShortSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ShortSeqHolder
implements Streamable {
    public short[] value;

    public ShortSeqHolder() {
    }

    public ShortSeqHolder(short[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ShortSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ShortSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ShortSeqHelper.write(out, this.value);
    }
}

