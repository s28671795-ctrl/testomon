/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.Handle;
import org.omg.ETF.HandleHelper;

public final class HandleHolder
implements Streamable {
    public Handle value;

    public HandleHolder() {
    }

    public HandleHolder(Handle initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return HandleHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = HandleHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        HandleHelper.write(_out, this.value);
    }
}

