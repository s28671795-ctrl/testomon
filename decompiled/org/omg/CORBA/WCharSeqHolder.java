/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WCharSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class WCharSeqHolder
implements Streamable {
    public char[] value;

    public WCharSeqHolder() {
    }

    public WCharSeqHolder(char[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return WCharSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = WCharSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        WCharSeqHelper.write(out, this.value);
    }
}

