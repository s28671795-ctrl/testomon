/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyHelper;

public final class DynAnyHolder
implements Streamable {
    public DynAny value;

    public DynAnyHolder() {
    }

    public DynAnyHolder(DynAny initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynAnyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynAnyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynAnyHelper.write(_out, this.value);
    }
}

