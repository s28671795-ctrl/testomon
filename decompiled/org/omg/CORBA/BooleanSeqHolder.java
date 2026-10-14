/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.BooleanSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class BooleanSeqHolder
implements Streamable {
    public boolean[] value;

    public BooleanSeqHolder() {
    }

    public BooleanSeqHolder(boolean[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BooleanSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = BooleanSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        BooleanSeqHelper.write(out, this.value);
    }
}

