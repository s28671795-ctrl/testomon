/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ThreadPolicyValue;
import org.omg.PortableServer.ThreadPolicyValueHelper;

public final class ThreadPolicyValueHolder
implements Streamable {
    public ThreadPolicyValue value;

    public ThreadPolicyValueHolder() {
    }

    public ThreadPolicyValueHolder(ThreadPolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ThreadPolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ThreadPolicyValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ThreadPolicyValueHelper.write(out, this.value);
    }
}

