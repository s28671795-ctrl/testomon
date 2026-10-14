/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.Protocol;
import org.omg.RTCORBA.ProtocolListHelper;

public final class ProtocolListHolder
implements Streamable {
    public Protocol[] value;

    public ProtocolListHolder() {
    }

    public ProtocolListHolder(Protocol[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ProtocolListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ProtocolListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ProtocolListHelper.write(out, this.value);
    }
}

