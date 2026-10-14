/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.Credentials;
import org.omg.SecurityLevel2.CredentialsListHelper;

public final class CredentialsListHolder
implements Streamable {
    public Credentials[] value;

    public CredentialsListHolder() {
    }

    public CredentialsListHolder(Credentials[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CredentialsListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CredentialsListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        CredentialsListHelper.write(out, this.value);
    }
}

