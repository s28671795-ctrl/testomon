/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.PortableInterceptor.ORBInitializerHelper;

public final class ORBInitializerHolder
implements Streamable {
    public ORBInitializer value;

    public ORBInitializerHolder() {
    }

    public ORBInitializerHolder(ORBInitializer initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ORBInitializerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ORBInitializerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ORBInitializerHelper.write(_out, this.value);
    }
}

