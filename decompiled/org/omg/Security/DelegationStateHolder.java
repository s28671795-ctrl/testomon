/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.DelegationState;
import org.omg.Security.DelegationStateHelper;

public final class DelegationStateHolder
implements Streamable {
    public DelegationState value;

    public DelegationStateHolder() {
    }

    public DelegationStateHolder(DelegationState initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DelegationStateHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DelegationStateHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DelegationStateHelper.write(out, this.value);
    }
}

