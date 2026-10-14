/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.PolicyFactory;
import org.omg.PortableInterceptor.PolicyFactoryHelper;

public final class PolicyFactoryHolder
implements Streamable {
    public PolicyFactory value;

    public PolicyFactoryHolder() {
    }

    public PolicyFactoryHolder(PolicyFactory initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyFactoryHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PolicyFactoryHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PolicyFactoryHelper.write(_out, this.value);
    }
}

