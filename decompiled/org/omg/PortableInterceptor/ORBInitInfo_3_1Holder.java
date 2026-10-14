/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ORBInitInfo_3_1;
import org.omg.PortableInterceptor.ORBInitInfo_3_1Helper;

public final class ORBInitInfo_3_1Holder
implements Streamable {
    public ORBInitInfo_3_1 value;

    public ORBInitInfo_3_1Holder() {
    }

    public ORBInitInfo_3_1Holder(ORBInitInfo_3_1 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ORBInitInfo_3_1Helper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ORBInitInfo_3_1Helper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ORBInitInfo_3_1Helper.write(_out, this.value);
    }
}

