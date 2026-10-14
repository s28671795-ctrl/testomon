/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UShortSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UShortSeqHolder
implements Streamable {
    public short[] value;

    public UShortSeqHolder() {
    }

    public UShortSeqHolder(short[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UShortSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = UShortSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        UShortSeqHelper.write(out, this.value);
    }
}

