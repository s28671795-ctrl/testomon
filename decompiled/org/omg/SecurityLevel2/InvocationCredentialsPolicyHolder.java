/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.InvocationCredentialsPolicy;
import org.omg.SecurityLevel2.InvocationCredentialsPolicyHelper;

public final class InvocationCredentialsPolicyHolder
implements Streamable {
    public InvocationCredentialsPolicy value;

    public InvocationCredentialsPolicyHolder() {
    }

    public InvocationCredentialsPolicyHolder(InvocationCredentialsPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvocationCredentialsPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = InvocationCredentialsPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvocationCredentialsPolicyHelper.write(_out, this.value);
    }
}

