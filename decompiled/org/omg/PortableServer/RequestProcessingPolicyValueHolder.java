/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.RequestProcessingPolicyValueHelper;

public final class RequestProcessingPolicyValueHolder
implements Streamable {
    public RequestProcessingPolicyValue value;

    public RequestProcessingPolicyValueHolder() {
    }

    public RequestProcessingPolicyValueHolder(RequestProcessingPolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestProcessingPolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequestProcessingPolicyValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        RequestProcessingPolicyValueHelper.write(out, this.value);
    }
}

