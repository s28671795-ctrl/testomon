/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynStruct;
import org.omg.DynamicAny.DynStructHelper;

public final class DynStructHolder
implements Streamable {
    public DynStruct value;

    public DynStructHolder() {
    }

    public DynStructHolder(DynStruct initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynStructHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynStructHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynStructHelper.write(_out, this.value);
    }
}

