/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.TCPProtocolProperties;
import org.omg.RTCORBA.TCPProtocolPropertiesHelper;

public final class TCPProtocolPropertiesHolder
implements Streamable {
    public TCPProtocolProperties value;

    public TCPProtocolPropertiesHolder() {
    }

    public TCPProtocolPropertiesHolder(TCPProtocolProperties initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TCPProtocolPropertiesHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = TCPProtocolPropertiesHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        TCPProtocolPropertiesHelper.write(_out, this.value);
    }
}

