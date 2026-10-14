/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.LifespanPolicyValueHelper;

public final class LifespanPolicyValueHolder
implements Streamable {
    public LifespanPolicyValue value;

    public LifespanPolicyValueHolder() {
    }

    public LifespanPolicyValueHolder(LifespanPolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LifespanPolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LifespanPolicyValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LifespanPolicyValueHelper.write(out, this.value);
    }
}

