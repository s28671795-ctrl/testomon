/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ServerRequestInterceptor;
import org.omg.PortableInterceptor.ServerRequestInterceptorHelper;

public final class ServerRequestInterceptorHolder
implements Streamable {
    public ServerRequestInterceptor value;

    public ServerRequestInterceptorHolder() {
    }

    public ServerRequestInterceptorHolder(ServerRequestInterceptor initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServerRequestInterceptorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServerRequestInterceptorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServerRequestInterceptorHelper.write(_out, this.value);
    }
}

