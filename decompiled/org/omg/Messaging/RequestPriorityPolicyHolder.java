/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RequestPriorityPolicy;
import org.omg.Messaging.RequestPriorityPolicyHelper;

public final class RequestPriorityPolicyHolder
implements Streamable {
    public RequestPriorityPolicy value;

    public RequestPriorityPolicyHolder() {
    }

    public RequestPriorityPolicyHolder(RequestPriorityPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestPriorityPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequestPriorityPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RequestPriorityPolicyHelper.write(_out, this.value);
    }
}

