/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SSLIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SSLIOP.SSL;
import org.omg.SSLIOP.SSLHelper;

public final class SSLHolder
implements Streamable {
    public SSL value;

    public SSLHolder() {
    }

    public SSLHolder(SSL initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SSLHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = SSLHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        SSLHelper.write(_out, this.value);
    }
}

