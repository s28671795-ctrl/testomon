/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ULongSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ULongSeqHolder
implements Streamable {
    public int[] value;

    public ULongSeqHolder() {
    }

    public ULongSeqHolder(int[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ULongSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ULongSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ULongSeqHelper.write(out, this.value);
    }
}

