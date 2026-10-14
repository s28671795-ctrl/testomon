/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyHelper;

public final class LifespanPolicyHolder
implements Streamable {
    public LifespanPolicy value;

    public LifespanPolicyHolder() {
    }

    public LifespanPolicyHolder(LifespanPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LifespanPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LifespanPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        LifespanPolicyHelper.write(_out, this.value);
    }
}

