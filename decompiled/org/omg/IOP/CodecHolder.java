/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.Codec;
import org.omg.IOP.CodecHelper;

public final class CodecHolder
implements Streamable {
    public Codec value;

    public CodecHolder() {
    }

    public CodecHolder(Codec initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CodecHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CodecHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        CodecHelper.write(_out, this.value);
    }
}

