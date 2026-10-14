/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynSequence;
import org.omg.DynamicAny.DynSequenceHelper;

public final class DynSequenceHolder
implements Streamable {
    public DynSequence value;

    public DynSequenceHolder() {
    }

    public DynSequenceHolder(DynSequence initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynSequenceHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynSequenceHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynSequenceHelper.write(_out, this.value);
    }
}

