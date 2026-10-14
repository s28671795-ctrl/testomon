/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RequestEndTimePolicy;
import org.omg.Messaging.RequestEndTimePolicyHelper;

public final class RequestEndTimePolicyHolder
implements Streamable {
    public RequestEndTimePolicy value;

    public RequestEndTimePolicyHolder() {
    }

    public RequestEndTimePolicyHolder(RequestEndTimePolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestEndTimePolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequestEndTimePolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RequestEndTimePolicyHelper.write(_out, this.value);
    }
}

