/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CharSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CharSeqHolder
implements Streamable {
    public char[] value;

    public CharSeqHolder() {
    }

    public CharSeqHolder(char[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CharSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CharSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        CharSeqHelper.write(out, this.value);
    }
}

