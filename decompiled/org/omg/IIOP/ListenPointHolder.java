/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IIOP.ListenPoint;
import org.omg.IIOP.ListenPointHelper;

public final class ListenPointHolder
implements Streamable {
    public ListenPoint value;

    public ListenPointHolder() {
    }

    public ListenPointHolder(ListenPoint initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ListenPointHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ListenPointHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ListenPointHelper.write(_out, this.value);
    }
}

