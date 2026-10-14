/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PolicyManager;
import org.omg.CORBA.PolicyManagerHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PolicyManagerHolder
implements Streamable {
    public PolicyManager value;

    public PolicyManagerHolder() {
    }

    public PolicyManagerHolder(PolicyManager initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyManagerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PolicyManagerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PolicyManagerHelper.write(_out, this.value);
    }
}

