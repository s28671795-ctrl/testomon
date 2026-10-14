/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityAdmin.DelegationPolicy;
import org.omg.SecurityAdmin.DelegationPolicyHelper;

public final class DelegationPolicyHolder
implements Streamable {
    public DelegationPolicy value;

    public DelegationPolicyHolder() {
    }

    public DelegationPolicyHolder(DelegationPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DelegationPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DelegationPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DelegationPolicyHelper.write(_out, this.value);
    }
}

