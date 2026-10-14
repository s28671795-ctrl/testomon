/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.FloatSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class FloatSeqHolder
implements Streamable {
    public float[] value;

    public FloatSeqHolder() {
    }

    public FloatSeqHolder(float[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FloatSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = FloatSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        FloatSeqHelper.write(out, this.value);
    }
}

