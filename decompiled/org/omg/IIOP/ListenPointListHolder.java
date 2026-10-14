/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IIOP.ListenPoint;
import org.omg.IIOP.ListenPointListHelper;

public final class ListenPointListHolder
implements Streamable {
    public ListenPoint[] value;

    public ListenPointListHolder() {
    }

    public ListenPointListHolder(ListenPoint[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ListenPointListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ListenPointListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ListenPointListHelper.write(out, this.value);
    }
}

