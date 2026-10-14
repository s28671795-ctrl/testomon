/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.omg.PortableInterceptor.ServerRequestInfoHelper;

public final class ServerRequestInfoHolder
implements Streamable {
    public ServerRequestInfo value;

    public ServerRequestInfoHolder() {
    }

    public ServerRequestInfoHolder(ServerRequestInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServerRequestInfoHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServerRequestInfoHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServerRequestInfoHelper.write(_out, this.value);
    }
}

