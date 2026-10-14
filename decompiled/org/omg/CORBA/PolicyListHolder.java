/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyListHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PolicyListHolder
implements Streamable {
    public Policy[] value;

    public PolicyListHolder() {
    }

    public PolicyListHolder(Policy[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PolicyListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PolicyListHelper.write(out, this.value);
    }
}

