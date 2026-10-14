/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynUnion;
import org.omg.DynamicAny.DynUnionHelper;

public final class DynUnionHolder
implements Streamable {
    public DynUnion value;

    public DynUnionHolder() {
    }

    public DynUnionHolder(DynUnion initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynUnionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynUnionHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynUnionHelper.write(_out, this.value);
    }
}

