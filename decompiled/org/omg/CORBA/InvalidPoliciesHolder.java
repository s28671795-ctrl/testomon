/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InvalidPolicies;
import org.omg.CORBA.InvalidPoliciesHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InvalidPoliciesHolder
implements Streamable {
    public InvalidPolicies value;

    public InvalidPoliciesHolder() {
    }

    public InvalidPoliciesHolder(InvalidPolicies initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidPoliciesHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidPoliciesHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidPoliciesHelper.write(_out, this.value);
    }
}

