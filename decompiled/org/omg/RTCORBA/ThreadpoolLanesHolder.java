/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.ThreadpoolLane;
import org.omg.RTCORBA.ThreadpoolLanesHelper;

public final class ThreadpoolLanesHolder
implements Streamable {
    public ThreadpoolLane[] value;

    public ThreadpoolLanesHolder() {
    }

    public ThreadpoolLanesHolder(ThreadpoolLane[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ThreadpoolLanesHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ThreadpoolLanesHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ThreadpoolLanesHelper.write(out, this.value);
    }
}

