/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PolicyError;
import org.omg.CORBA.PolicyErrorHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PolicyErrorHolder
implements Streamable {
    public PolicyError value;

    public PolicyErrorHolder() {
    }

    public PolicyErrorHolder(PolicyError initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyErrorHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = PolicyErrorHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        PolicyErrorHelper.write(_out, this.value);
    }
}

