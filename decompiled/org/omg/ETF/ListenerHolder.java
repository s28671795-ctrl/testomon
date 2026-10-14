/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.Listener;
import org.omg.ETF.ListenerHelper;

public final class ListenerHolder
implements Streamable {
    public Listener value;

    public ListenerHolder() {
    }

    public ListenerHolder(Listener initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ListenerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ListenerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ListenerHelper.write(_out, this.value);
    }
}

