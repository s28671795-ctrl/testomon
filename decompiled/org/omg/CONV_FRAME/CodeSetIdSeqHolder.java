/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import org.omg.CONV_FRAME.CodeSetIdSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CodeSetIdSeqHolder
implements Streamable {
    public int[] value;

    public CodeSetIdSeqHolder() {
    }

    public CodeSetIdSeqHolder(int[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CodeSetIdSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CodeSetIdSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        CodeSetIdSeqHelper.write(out, this.value);
    }
}

