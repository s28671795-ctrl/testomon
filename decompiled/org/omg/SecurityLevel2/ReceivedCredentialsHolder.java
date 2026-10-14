/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.ReceivedCredentials;
import org.omg.SecurityLevel2.ReceivedCredentialsHelper;

public final class ReceivedCredentialsHolder
implements Streamable {
    public ReceivedCredentials value;

    public ReceivedCredentialsHolder() {
    }

    public ReceivedCredentialsHolder(ReceivedCredentials initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ReceivedCredentialsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ReceivedCredentialsHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ReceivedCredentialsHelper.write(_out, this.value);
    }
}

