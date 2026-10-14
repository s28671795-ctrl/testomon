/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.OIDListHelper;

public final class OIDListHolder
implements Streamable {
    public byte[][] value;

    public OIDListHolder() {
    }

    public OIDListHolder(byte[][] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OIDListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OIDListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        OIDListHelper.write(out, this.value);
    }
}

