/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RebindPolicy;
import org.omg.Messaging.RebindPolicyHelper;

public final class RebindPolicyHolder
implements Streamable {
    public RebindPolicy value;

    public RebindPolicyHolder() {
    }

    public RebindPolicyHolder(RebindPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RebindPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RebindPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RebindPolicyHelper.write(_out, this.value);
    }
}

