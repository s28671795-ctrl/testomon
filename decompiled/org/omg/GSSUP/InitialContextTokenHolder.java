/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GSSUP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GSSUP.InitialContextToken;
import org.omg.GSSUP.InitialContextTokenHelper;

public final class InitialContextTokenHolder
implements Streamable {
    public InitialContextToken value;

    public InitialContextTokenHolder() {
    }

    public InitialContextTokenHolder(InitialContextToken initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InitialContextTokenHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InitialContextTokenHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InitialContextTokenHelper.write(_out, this.value);
    }
}

