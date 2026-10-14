/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.PriorityBandedConnectionPolicy;
import org.omg.RTCORBA.PriorityBandedConnectionPolicyHelper;

public final class PriorityBandedConnectionPolicyHolder
implements Streamable {
    public PriorityBandedConnectionPolicy value;

    public PriorityBandedConnectionPolicyHolder() {
    }

    public PriorityBandedConnectionPolicyHolder(PriorityBandedConnectionPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PriorityBandedConnectionPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PriorityBandedConnectionPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PriorityBandedConnectionPolicyHelper.write(_out, this.value);
    }
}

