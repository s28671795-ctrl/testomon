/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityAdmin.SecureInvocationPolicy;
import org.omg.SecurityAdmin.SecureInvocationPolicyHelper;

public final class SecureInvocationPolicyHolder
implements Streamable {
    public SecureInvocationPolicy value;

    public SecureInvocationPolicyHolder() {
    }

    public SecureInvocationPolicyHolder(SecureInvocationPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SecureInvocationPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SecureInvocationPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        SecureInvocationPolicyHelper.write(_out, this.value);
    }
}

