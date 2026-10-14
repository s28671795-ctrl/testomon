/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.Protocol;
import org.omg.RTCORBA.ProtocolHelper;

public final class ProtocolHolder
implements Streamable {
    public Protocol value;

    public ProtocolHolder() {
    }

    public ProtocolHolder(Protocol initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ProtocolHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ProtocolHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ProtocolHelper.write(_out, this.value);
    }
}

