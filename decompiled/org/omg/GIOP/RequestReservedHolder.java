/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.RequestReservedHelper;

public final class RequestReservedHolder
implements Streamable {
    public byte[] value;

    public RequestReservedHolder() {
    }

    public RequestReservedHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestReservedHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequestReservedHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        RequestReservedHelper.write(out, this.value);
    }
}

