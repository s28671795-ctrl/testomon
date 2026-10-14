/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynArray;
import org.omg.DynamicAny.DynArrayHelper;

public final class DynArrayHolder
implements Streamable {
    public DynArray value;

    public DynArrayHolder() {
    }

    public DynArrayHolder(DynArray initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynArrayHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynArrayHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynArrayHelper.write(_out, this.value);
    }
}

