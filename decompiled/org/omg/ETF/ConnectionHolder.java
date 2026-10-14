/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.Connection;
import org.omg.ETF.ConnectionHelper;

public final class ConnectionHolder
implements Streamable {
    public Connection value;

    public ConnectionHolder() {
    }

    public ConnectionHolder(Connection initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ConnectionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ConnectionHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ConnectionHelper.write(_out, this.value);
    }
}

