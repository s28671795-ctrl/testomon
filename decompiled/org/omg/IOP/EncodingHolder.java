/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.Encoding;
import org.omg.IOP.EncodingHelper;

public final class EncodingHolder
implements Streamable {
    public Encoding value;

    public EncodingHolder() {
    }

    public EncodingHolder(Encoding initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return EncodingHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = EncodingHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        EncodingHelper.write(_out, this.value);
    }
}

