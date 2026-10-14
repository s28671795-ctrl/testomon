/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.CancelRequestHeader;
import org.omg.GIOP.CancelRequestHeaderHelper;

public final class CancelRequestHeaderHolder
implements Streamable {
    public CancelRequestHeader value;

    public CancelRequestHeaderHolder() {
    }

    public CancelRequestHeaderHolder(CancelRequestHeader initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CancelRequestHeaderHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CancelRequestHeaderHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CancelRequestHeaderHelper.write(_out, this.value);
    }
}

