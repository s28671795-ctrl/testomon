/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitInfoHelper;

public final class ORBInitInfoHolder
implements Streamable {
    public ORBInitInfo value;

    public ORBInitInfoHolder() {
    }

    public ORBInitInfoHolder(ORBInitInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ORBInitInfoHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ORBInitInfoHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ORBInitInfoHelper.write(_out, this.value);
    }
}

