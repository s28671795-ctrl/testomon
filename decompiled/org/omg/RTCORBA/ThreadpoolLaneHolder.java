/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.ThreadpoolLane;
import org.omg.RTCORBA.ThreadpoolLaneHelper;

public final class ThreadpoolLaneHolder
implements Streamable {
    public ThreadpoolLane value;

    public ThreadpoolLaneHolder() {
    }

    public ThreadpoolLaneHolder(ThreadpoolLane initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ThreadpoolLaneHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ThreadpoolLaneHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ThreadpoolLaneHelper.write(_out, this.value);
    }
}

