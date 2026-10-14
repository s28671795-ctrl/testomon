/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.PolicyValue;
import org.omg.Messaging.PolicyValueHelper;

public final class PolicyValueHolder
implements Streamable {
    public PolicyValue value;

    public PolicyValueHolder() {
    }

    public PolicyValueHolder(PolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = PolicyValueHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        PolicyValueHelper.write(_out, this.value);
    }
}

