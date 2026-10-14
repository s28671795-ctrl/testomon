/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ClientRequestInterceptorHelper;

public final class ClientRequestInterceptorHolder
implements Streamable {
    public ClientRequestInterceptor value;

    public ClientRequestInterceptorHolder() {
    }

    public ClientRequestInterceptorHolder(ClientRequestInterceptor initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ClientRequestInterceptorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ClientRequestInterceptorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ClientRequestInterceptorHelper.write(_out, this.value);
    }
}

