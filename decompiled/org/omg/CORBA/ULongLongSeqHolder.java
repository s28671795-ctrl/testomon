/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ULongLongSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ULongLongSeqHolder
implements Streamable {
    public long[] value;

    public ULongLongSeqHolder() {
    }

    public ULongLongSeqHolder(long[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ULongLongSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ULongLongSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ULongLongSeqHelper.write(out, this.value);
    }
}

