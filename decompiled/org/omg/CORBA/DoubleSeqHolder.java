/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DoubleSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DoubleSeqHolder
implements Streamable {
    public double[] value;

    public DoubleSeqHolder() {
    }

    public DoubleSeqHolder(double[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DoubleSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DoubleSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DoubleSeqHelper.write(out, this.value);
    }
}

