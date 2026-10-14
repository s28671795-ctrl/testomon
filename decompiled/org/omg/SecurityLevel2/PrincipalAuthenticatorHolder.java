/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.PrincipalAuthenticator;
import org.omg.SecurityLevel2.PrincipalAuthenticatorHelper;

public final class PrincipalAuthenticatorHolder
implements Streamable {
    public PrincipalAuthenticator value;

    public PrincipalAuthenticatorHolder() {
    }

    public PrincipalAuthenticatorHolder(PrincipalAuthenticator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PrincipalAuthenticatorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PrincipalAuthenticatorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PrincipalAuthenticatorHelper.write(_out, this.value);
    }
}

