/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.X501DistinguishedNameHelper;

public final class X501DistinguishedNameHolder
implements Streamable {
    public byte[] value;

    public X501DistinguishedNameHolder() {
    }

    public X501DistinguishedNameHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return X501DistinguishedNameHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = X501DistinguishedNameHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        X501DistinguishedNameHelper.write(out, this.value);
    }
}

