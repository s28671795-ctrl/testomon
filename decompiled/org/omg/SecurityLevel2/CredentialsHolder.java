/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.Credentials;
import org.omg.SecurityLevel2.CredentialsHelper;

public final class CredentialsHolder
implements Streamable {
    public Credentials value;

    public CredentialsHolder() {
    }

    public CredentialsHolder(Credentials initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CredentialsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CredentialsHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        CredentialsHelper.write(_out, this.value);
    }
}

