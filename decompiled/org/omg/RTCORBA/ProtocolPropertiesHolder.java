/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.ProtocolProperties;
import org.omg.RTCORBA.ProtocolPropertiesHelper;

public final class ProtocolPropertiesHolder
implements Streamable {
    public ProtocolProperties value;

    public ProtocolPropertiesHolder() {
    }

    public ProtocolPropertiesHolder(ProtocolProperties initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ProtocolPropertiesHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ProtocolPropertiesHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ProtocolPropertiesHelper.write(_out, this.value);
    }
}

