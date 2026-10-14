/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.PrivateConnectionPolicy;
import org.omg.RTCORBA.PrivateConnectionPolicyHelper;

public final class PrivateConnectionPolicyHolder
implements Streamable {
    public PrivateConnectionPolicy value;

    public PrivateConnectionPolicyHolder() {
    }

    public PrivateConnectionPolicyHolder(PrivateConnectionPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PrivateConnectionPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PrivateConnectionPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PrivateConnectionPolicyHelper.write(_out, this.value);
    }
}

