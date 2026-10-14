/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.ConnectionZeroCopy;
import org.omg.ETF.ConnectionZeroCopyHelper;

public final class ConnectionZeroCopyHolder
implements Streamable {
    public ConnectionZeroCopy value;

    public ConnectionZeroCopyHolder() {
    }

    public ConnectionZeroCopyHolder(ConnectionZeroCopy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ConnectionZeroCopyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ConnectionZeroCopyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ConnectionZeroCopyHelper.write(_out, this.value);
    }
}

