/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.OpaqueHelper;

public final class OpaqueHolder
implements Streamable {
    public byte[] value;

    public OpaqueHolder() {
    }

    public OpaqueHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OpaqueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OpaqueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        OpaqueHelper.write(out, this.value);
    }
}

