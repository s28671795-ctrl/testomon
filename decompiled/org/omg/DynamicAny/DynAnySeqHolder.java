/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnySeqHelper;

public final class DynAnySeqHolder
implements Streamable {
    public DynAny[] value;

    public DynAnySeqHolder() {
    }

    public DynAnySeqHolder(DynAny[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynAnySeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynAnySeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DynAnySeqHelper.write(out, this.value);
    }
}

