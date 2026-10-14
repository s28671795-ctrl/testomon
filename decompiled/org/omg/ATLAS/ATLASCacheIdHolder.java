/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.ATLASCacheIdHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ATLASCacheIdHolder
implements Streamable {
    public byte[] value;

    public ATLASCacheIdHolder() {
    }

    public ATLASCacheIdHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ATLASCacheIdHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ATLASCacheIdHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ATLASCacheIdHelper.write(out, this.value);
    }
}

