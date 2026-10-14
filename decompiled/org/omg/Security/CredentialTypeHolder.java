/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.CredentialType;
import org.omg.Security.CredentialTypeHelper;

public final class CredentialTypeHolder
implements Streamable {
    public CredentialType value;

    public CredentialTypeHolder() {
    }

    public CredentialTypeHolder(CredentialType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CredentialTypeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CredentialTypeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        CredentialTypeHelper.write(out, this.value);
    }
}

