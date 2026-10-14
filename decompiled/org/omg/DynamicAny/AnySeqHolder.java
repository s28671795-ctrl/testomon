/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.AnySeqHelper;

public final class AnySeqHolder
implements Streamable {
    public Any[] value;

    public AnySeqHolder() {
    }

    public AnySeqHolder(Any[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AnySeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AnySeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AnySeqHelper.write(out, this.value);
    }
}

