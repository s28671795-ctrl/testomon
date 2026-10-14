/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.AuthTokenDispenser;
import org.omg.ATLAS.AuthTokenDispenserHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AuthTokenDispenserHolder
implements Streamable {
    public AuthTokenDispenser value;

    public AuthTokenDispenserHolder() {
    }

    public AuthTokenDispenserHolder(AuthTokenDispenser initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuthTokenDispenserHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuthTokenDispenserHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AuthTokenDispenserHelper.write(_out, this.value);
    }
}

