/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.RTORB;
import org.omg.RTCORBA.RTORBHelper;

public final class RTORBHolder
implements Streamable {
    public RTORB value;

    public RTORBHolder() {
    }

    public RTORBHolder(RTORB initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RTORBHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RTORBHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RTORBHelper.write(_out, this.value);
    }
}

