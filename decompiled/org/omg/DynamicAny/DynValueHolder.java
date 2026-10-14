/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynValue;
import org.omg.DynamicAny.DynValueHelper;

public final class DynValueHolder
implements Streamable {
    public DynValue value;

    public DynValueHolder() {
    }

    public DynValueHolder(DynValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynValueHelper.write(_out, this.value);
    }
}

