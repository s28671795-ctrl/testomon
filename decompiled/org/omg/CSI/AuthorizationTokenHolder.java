/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.AuthorizationTokenHelper;

public final class AuthorizationTokenHolder
implements Streamable {
    public AuthorizationElement[] value;

    public AuthorizationTokenHolder() {
    }

    public AuthorizationTokenHolder(AuthorizationElement[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuthorizationTokenHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuthorizationTokenHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AuthorizationTokenHelper.write(out, this.value);
    }
}

