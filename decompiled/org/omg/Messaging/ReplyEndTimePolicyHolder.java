/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.ReplyEndTimePolicy;
import org.omg.Messaging.ReplyEndTimePolicyHelper;

public final class ReplyEndTimePolicyHolder
implements Streamable {
    public ReplyEndTimePolicy value;

    public ReplyEndTimePolicyHolder() {
    }

    public ReplyEndTimePolicyHolder(ReplyEndTimePolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ReplyEndTimePolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ReplyEndTimePolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ReplyEndTimePolicyHelper.write(_out, this.value);
    }
}

