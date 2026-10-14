/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.InvocationCredentialsType;
import org.omg.Security.InvocationCredentialsTypeHelper;

public final class InvocationCredentialsTypeHolder
implements Streamable {
    public InvocationCredentialsType value;

    public InvocationCredentialsTypeHolder() {
    }

    public InvocationCredentialsTypeHolder(InvocationCredentialsType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvocationCredentialsTypeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = InvocationCredentialsTypeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        InvocationCredentialsTypeHelper.write(out, this.value);
    }
}

