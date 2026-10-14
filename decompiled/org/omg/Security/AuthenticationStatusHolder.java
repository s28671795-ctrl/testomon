/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AuthenticationStatus;
import org.omg.Security.AuthenticationStatusHelper;

public final class AuthenticationStatusHolder
implements Streamable {
    public AuthenticationStatus value;

    public AuthenticationStatusHolder() {
    }

    public AuthenticationStatusHolder(AuthenticationStatus initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuthenticationStatusHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuthenticationStatusHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AuthenticationStatusHelper.write(out, this.value);
    }
}

