/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.GSSTokenHelper;

public final class GSSTokenHolder
implements Streamable {
    public byte[] value;

    public GSSTokenHolder() {
    }

    public GSSTokenHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return GSSTokenHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = GSSTokenHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        GSSTokenHelper.write(out, this.value);
    }
}

