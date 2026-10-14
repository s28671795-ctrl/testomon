/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.RoutingPolicy;
import org.omg.Messaging.RoutingPolicyHelper;

public final class RoutingPolicyHolder
implements Streamable {
    public RoutingPolicy value;

    public RoutingPolicyHolder() {
    }

    public RoutingPolicyHolder(RoutingPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RoutingPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RoutingPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RoutingPolicyHelper.write(_out, this.value);
    }
}

