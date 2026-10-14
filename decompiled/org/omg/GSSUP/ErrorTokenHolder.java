/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GSSUP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GSSUP.ErrorToken;
import org.omg.GSSUP.ErrorTokenHelper;

public final class ErrorTokenHolder
implements Streamable {
    public ErrorToken value;

    public ErrorTokenHolder() {
    }

    public ErrorTokenHolder(ErrorToken initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ErrorTokenHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ErrorTokenHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ErrorTokenHelper.write(_out, this.value);
    }
}

