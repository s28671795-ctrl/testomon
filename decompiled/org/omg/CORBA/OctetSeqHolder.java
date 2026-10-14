/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class OctetSeqHolder
implements Streamable {
    public byte[] value;

    public OctetSeqHolder() {
    }

    public OctetSeqHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OctetSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OctetSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        OctetSeqHelper.write(out, this.value);
    }
}

