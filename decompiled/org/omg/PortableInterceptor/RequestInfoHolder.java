/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.RequestInfo;
import org.omg.PortableInterceptor.RequestInfoHelper;

public final class RequestInfoHolder
implements Streamable {
    public RequestInfo value;

    public RequestInfoHolder() {
    }

    public RequestInfoHolder(RequestInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestInfoHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequestInfoHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RequestInfoHelper.write(_out, this.value);
    }
}

