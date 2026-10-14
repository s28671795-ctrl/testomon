/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.IORInterceptor_3_0;
import org.omg.PortableInterceptor.IORInterceptor_3_0Helper;

public final class IORInterceptor_3_0Holder
implements Streamable {
    public IORInterceptor_3_0 value;

    public IORInterceptor_3_0Holder() {
    }

    public IORInterceptor_3_0Holder(IORInterceptor_3_0 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IORInterceptor_3_0Helper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IORInterceptor_3_0Helper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        IORInterceptor_3_0Helper.write(_out, this.value);
    }
}

