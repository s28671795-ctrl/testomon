/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;
import org.omg.IOP.CodecPackage.InvalidTypeForEncodingHelper;

public final class InvalidTypeForEncodingHolder
implements Streamable {
    public InvalidTypeForEncoding value;

    public InvalidTypeForEncodingHolder() {
    }

    public InvalidTypeForEncodingHolder(InvalidTypeForEncoding initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidTypeForEncodingHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidTypeForEncodingHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidTypeForEncodingHelper.write(_out, this.value);
    }
}

