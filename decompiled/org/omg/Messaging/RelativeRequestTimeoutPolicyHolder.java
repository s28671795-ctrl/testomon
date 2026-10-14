/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RelativeRequestTimeoutPolicy;
import org.omg.Messaging.RelativeRequestTimeoutPolicyHelper;

public final class RelativeRequestTimeoutPolicyHolder
implements Streamable {
    public RelativeRequestTimeoutPolicy value;

    public RelativeRequestTimeoutPolicyHolder() {
    }

    public RelativeRequestTimeoutPolicyHolder(RelativeRequestTimeoutPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RelativeRequestTimeoutPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RelativeRequestTimeoutPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RelativeRequestTimeoutPolicyHelper.write(_out, this.value);
    }
}

