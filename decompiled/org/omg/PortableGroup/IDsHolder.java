/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.IDsHelper;

public final class IDsHolder
implements Streamable {
    public byte[][] value;

    public IDsHolder() {
    }

    public IDsHolder(byte[][] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IDsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IDsHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        IDsHelper.write(out, this.value);
    }
}

