/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LongDoubleSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class LongDoubleSeqHolder
implements Streamable {
    public double[] value;

    public LongDoubleSeqHolder() {
    }

    public LongDoubleSeqHolder(double[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LongDoubleSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LongDoubleSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LongDoubleSeqHelper.write(out, this.value);
    }
}

