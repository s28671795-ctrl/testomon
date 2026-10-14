/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.PortableInterceptor.IORInterceptorHelper;

public final class IORInterceptorHolder
implements Streamable {
    public IORInterceptor value;

    public IORInterceptorHolder() {
    }

    public IORInterceptorHolder(IORInterceptor initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IORInterceptorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IORInterceptorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        IORInterceptorHelper.write(_out, this.value);
    }
}

