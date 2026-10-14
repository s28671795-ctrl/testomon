/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.ThreadpoolPolicy;
import org.omg.RTCORBA.ThreadpoolPolicyHelper;

public final class ThreadpoolPolicyHolder
implements Streamable {
    public ThreadpoolPolicy value;

    public ThreadpoolPolicyHolder() {
    }

    public ThreadpoolPolicyHolder(ThreadpoolPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ThreadpoolPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ThreadpoolPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ThreadpoolPolicyHelper.write(_out, this.value);
    }
}

