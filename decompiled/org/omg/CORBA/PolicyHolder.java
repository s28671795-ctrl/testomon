/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PolicyHolder
implements Streamable {
    public Policy value;

    public PolicyHolder() {
    }

    public PolicyHolder(Policy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PolicyHelper.write(_out, this.value);
    }
}

