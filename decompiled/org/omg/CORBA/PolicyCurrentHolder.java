/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PolicyCurrent;
import org.omg.CORBA.PolicyCurrentHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PolicyCurrentHolder
implements Streamable {
    public PolicyCurrent value;

    public PolicyCurrentHolder() {
    }

    public PolicyCurrentHolder(PolicyCurrent initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyCurrentHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PolicyCurrentHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PolicyCurrentHelper.write(_out, this.value);
    }
}

