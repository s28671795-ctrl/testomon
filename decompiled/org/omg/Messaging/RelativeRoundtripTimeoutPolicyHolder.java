/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RelativeRoundtripTimeoutPolicy;
import org.omg.Messaging.RelativeRoundtripTimeoutPolicyHelper;

public final class RelativeRoundtripTimeoutPolicyHolder
implements Streamable {
    public RelativeRoundtripTimeoutPolicy value;

    public RelativeRoundtripTimeoutPolicyHolder() {
    }

    public RelativeRoundtripTimeoutPolicyHolder(RelativeRoundtripTimeoutPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RelativeRoundtripTimeoutPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RelativeRoundtripTimeoutPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RelativeRoundtripTimeoutPolicyHelper.write(_out, this.value);
    }
}

