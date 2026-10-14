/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.UTF8StringHelper;

public final class UTF8StringHolder
implements Streamable {
    public byte[] value;

    public UTF8StringHolder() {
    }

    public UTF8StringHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UTF8StringHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = UTF8StringHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        UTF8StringHelper.write(out, this.value);
    }
}

