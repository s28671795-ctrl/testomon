/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyHelper;

public final class RequestProcessingPolicyHolder
implements Streamable {
    public RequestProcessingPolicy value;

    public RequestProcessingPolicyHolder() {
    }

    public RequestProcessingPolicyHolder(RequestProcessingPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestProcessingPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequestProcessingPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RequestProcessingPolicyHelper.write(_out, this.value);
    }
}

