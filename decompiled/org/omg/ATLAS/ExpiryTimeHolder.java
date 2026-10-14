/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.ExpiryTimeHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.TimeBase.UtcT;

public final class ExpiryTimeHolder
implements Streamable {
    public UtcT[] value;

    public ExpiryTimeHolder() {
    }

    public ExpiryTimeHolder(UtcT[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExpiryTimeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExpiryTimeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExpiryTimeHelper.write(out, this.value);
    }
}

