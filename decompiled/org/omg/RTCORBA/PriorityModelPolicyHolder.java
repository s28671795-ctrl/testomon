/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.PriorityModelPolicy;
import org.omg.RTCORBA.PriorityModelPolicyHelper;

public final class PriorityModelPolicyHolder
implements Streamable {
    public PriorityModelPolicy value;

    public PriorityModelPolicyHolder() {
    }

    public PriorityModelPolicyHolder(PriorityModelPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PriorityModelPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PriorityModelPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PriorityModelPolicyHelper.write(_out, this.value);
    }
}

