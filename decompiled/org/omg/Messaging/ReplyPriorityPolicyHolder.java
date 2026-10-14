/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.ReplyPriorityPolicy;
import org.omg.Messaging.ReplyPriorityPolicyHelper;

public final class ReplyPriorityPolicyHolder
implements Streamable {
    public ReplyPriorityPolicy value;

    public ReplyPriorityPolicyHolder() {
    }

    public ReplyPriorityPolicyHolder(ReplyPriorityPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ReplyPriorityPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ReplyPriorityPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ReplyPriorityPolicyHelper.write(_out, this.value);
    }
}

