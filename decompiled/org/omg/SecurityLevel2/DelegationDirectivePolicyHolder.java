/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.DelegationDirectivePolicy;
import org.omg.SecurityLevel2.DelegationDirectivePolicyHelper;

public final class DelegationDirectivePolicyHolder
implements Streamable {
    public DelegationDirectivePolicy value;

    public DelegationDirectivePolicyHolder() {
    }

    public DelegationDirectivePolicyHolder(DelegationDirectivePolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DelegationDirectivePolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DelegationDirectivePolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DelegationDirectivePolicyHelper.write(_out, this.value);
    }
}

