/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LongSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class LongSeqHolder
implements Streamable {
    public int[] value;

    public LongSeqHolder() {
    }

    public LongSeqHolder(int[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LongSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LongSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LongSeqHelper.write(out, this.value);
    }
}

