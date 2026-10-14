/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.PolicyValue;
import org.omg.Messaging.PolicyValueSeqHelper;

public final class PolicyValueSeqHolder
implements Streamable {
    public PolicyValue[] value;

    public PolicyValueSeqHolder() {
    }

    public PolicyValueSeqHolder(PolicyValue[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyValueSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PolicyValueSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PolicyValueSeqHelper.write(out, this.value);
    }
}

