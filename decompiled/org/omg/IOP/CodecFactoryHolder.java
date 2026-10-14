/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.CodecFactory;
import org.omg.IOP.CodecFactoryHelper;

public final class CodecFactoryHolder
implements Streamable {
    public CodecFactory value;

    public CodecFactoryHolder() {
    }

    public CodecFactoryHolder(CodecFactory initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CodecFactoryHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CodecFactoryHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        CodecFactoryHelper.write(_out, this.value);
    }
}

