/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.AuthorizationElementContentsHelper;

public final class AuthorizationElementContentsHolder
implements Streamable {
    public byte[] value;

    public AuthorizationElementContentsHolder() {
    }

    public AuthorizationElementContentsHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuthorizationElementContentsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuthorizationElementContentsHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AuthorizationElementContentsHelper.write(out, this.value);
    }
}

