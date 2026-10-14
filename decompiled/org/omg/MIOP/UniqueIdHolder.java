/*
 * Decompiled with CFR 0.152.
 */
package org.omg.MIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.MIOP.UniqueIdHelper;

public final class UniqueIdHolder
implements Streamable {
    public byte[] value;

    public UniqueIdHolder() {
    }

    public UniqueIdHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UniqueIdHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = UniqueIdHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        UniqueIdHelper.write(out, this.value);
    }
}

