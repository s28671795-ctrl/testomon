/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecFactoryPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;
import org.omg.IOP.CodecFactoryPackage.UnknownEncodingHelper;

public final class UnknownEncodingHolder
implements Streamable {
    public UnknownEncoding value;

    public UnknownEncodingHolder() {
    }

    public UnknownEncodingHolder(UnknownEncoding initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UnknownEncodingHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = UnknownEncodingHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        UnknownEncodingHelper.write(_out, this.value);
    }
}

