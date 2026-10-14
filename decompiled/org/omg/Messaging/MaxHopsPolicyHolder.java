/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.MaxHopsPolicy;
import org.omg.Messaging.MaxHopsPolicyHelper;

public final class MaxHopsPolicyHolder
implements Streamable {
    public MaxHopsPolicy value;

    public MaxHopsPolicyHolder() {
    }

    public MaxHopsPolicyHolder(MaxHopsPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MaxHopsPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MaxHopsPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        MaxHopsPolicyHelper.write(_out, this.value);
    }
}

