/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.ReplyStartTimePolicy;
import org.omg.Messaging.ReplyStartTimePolicyHelper;

public final class ReplyStartTimePolicyHolder
implements Streamable {
    public ReplyStartTimePolicy value;

    public ReplyStartTimePolicyHolder() {
    }

    public ReplyStartTimePolicyHolder(ReplyStartTimePolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ReplyStartTimePolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ReplyStartTimePolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ReplyStartTimePolicyHelper.write(_out, this.value);
    }
}

