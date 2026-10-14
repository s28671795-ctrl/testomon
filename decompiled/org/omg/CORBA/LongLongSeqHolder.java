/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LongLongSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class LongLongSeqHolder
implements Streamable {
    public long[] value;

    public LongLongSeqHolder() {
    }

    public LongLongSeqHolder(long[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LongLongSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LongLongSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LongLongSeqHelper.write(out, this.value);
    }
}

