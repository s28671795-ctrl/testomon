/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.BufferList;
import org.omg.ETF.BufferListHelper;

public final class BufferListHolder
implements Streamable {
    public BufferList value;

    public BufferListHolder() {
    }

    public BufferListHolder(BufferList initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BufferListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = BufferListHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        BufferListHelper.write(_out, this.value);
    }
}

