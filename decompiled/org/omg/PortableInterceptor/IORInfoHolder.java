/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.IORInfo;
import org.omg.PortableInterceptor.IORInfoHelper;

public final class IORInfoHolder
implements Streamable {
    public IORInfo value;

    public IORInfoHolder() {
    }

    public IORInfoHolder(IORInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IORInfoHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IORInfoHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        IORInfoHelper.write(_out, this.value);
    }
}

