/*
 * Decompiled with CFR 0.152.
 */
package org.omg.TimeBase;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.TimeBase.UtcT;
import org.omg.TimeBase.UtcTHelper;

public final class UtcTHolder
implements Streamable {
    public UtcT value;

    public UtcTHolder() {
    }

    public UtcTHolder(UtcT initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UtcTHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = UtcTHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        UtcTHelper.write(_out, this.value);
    }
}

