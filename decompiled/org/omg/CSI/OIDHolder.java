/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.OIDHelper;

public final class OIDHolder
implements Streamable {
    public byte[] value;

    public OIDHolder() {
    }

    public OIDHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OIDHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OIDHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        OIDHelper.write(out, this.value);
    }
}

