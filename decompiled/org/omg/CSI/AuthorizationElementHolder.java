/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.AuthorizationElementHelper;

public final class AuthorizationElementHolder
implements Streamable {
    public AuthorizationElement value;

    public AuthorizationElementHolder() {
    }

    public AuthorizationElementHolder(AuthorizationElement initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuthorizationElementHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AuthorizationElementHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AuthorizationElementHelper.write(_out, this.value);
    }
}

