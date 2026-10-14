/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.AuthTokenData;
import org.omg.ATLAS.AuthTokenDataHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AuthTokenDataHolder
implements Streamable {
    public AuthTokenData value;

    public AuthTokenDataHolder() {
    }

    public AuthTokenDataHolder(AuthTokenData initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuthTokenDataHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AuthTokenDataHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AuthTokenDataHelper.write(_out, this.value);
    }
}

