/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.TokenOkay;
import org.omg.ATLAS.TokenOkayHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TokenOkayHolder
implements Streamable {
    public TokenOkay value;

    public TokenOkayHolder() {
    }

    public TokenOkayHolder(TokenOkay initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TokenOkayHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TokenOkayHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TokenOkayHelper.write(_out, this.value);
    }
}

