/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.MagicnHelper;

public final class MagicnHolder
implements Streamable {
    public char[] value;

    public MagicnHolder() {
    }

    public MagicnHolder(char[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MagicnHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MagicnHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        MagicnHelper.write(out, this.value);
    }
}

