/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.PrincipalHelper;

public final class PrincipalHolder
implements Streamable {
    public byte[] value;

    public PrincipalHolder() {
    }

    public PrincipalHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PrincipalHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PrincipalHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PrincipalHelper.write(out, this.value);
    }
}

