/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RequestStartTimePolicy;
import org.omg.Messaging.RequestStartTimePolicyHelper;

public final class RequestStartTimePolicyHolder
implements Streamable {
    public RequestStartTimePolicy value;

    public RequestStartTimePolicyHolder() {
    }

    public RequestStartTimePolicyHolder(RequestStartTimePolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestStartTimePolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequestStartTimePolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RequestStartTimePolicyHelper.write(_out, this.value);
    }
}

