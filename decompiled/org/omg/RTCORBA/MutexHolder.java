/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.Mutex;
import org.omg.RTCORBA.MutexHelper;

public final class MutexHolder
implements Streamable {
    public Mutex value;

    public MutexHolder() {
    }

    public MutexHolder(Mutex initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MutexHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MutexHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        MutexHelper.write(_out, this.value);
    }
}

