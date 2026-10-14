/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ClientRequestInfoHelper;

public final class ClientRequestInfoHolder
implements Streamable {
    public ClientRequestInfo value;

    public ClientRequestInfoHolder() {
    }

    public ClientRequestInfoHolder(ClientRequestInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ClientRequestInfoHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ClientRequestInfoHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ClientRequestInfoHelper.write(_out, this.value);
    }
}

