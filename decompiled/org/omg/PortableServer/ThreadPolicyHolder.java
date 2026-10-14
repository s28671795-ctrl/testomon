/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyHelper;

public final class ThreadPolicyHolder
implements Streamable {
    public ThreadPolicy value;

    public ThreadPolicyHolder() {
    }

    public ThreadPolicyHolder(ThreadPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ThreadPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ThreadPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ThreadPolicyHelper.write(_out, this.value);
    }
}

