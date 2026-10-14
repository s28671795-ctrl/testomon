/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AuthenticationMethodListHelper;

public final class AuthenticationMethodListHolder
implements Streamable {
    public int[] value;

    public AuthenticationMethodListHolder() {
    }

    public AuthenticationMethodListHolder(int[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuthenticationMethodListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuthenticationMethodListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AuthenticationMethodListHelper.write(out, this.value);
    }
}

