/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.Interceptor;
import org.omg.PortableInterceptor.InterceptorHelper;

public final class InterceptorHolder
implements Streamable {
    public Interceptor value;

    public InterceptorHolder() {
    }

    public InterceptorHolder(Interceptor initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InterceptorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = InterceptorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        InterceptorHelper.write(_out, this.value);
    }
}

