/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ContextIdSeqHolder
implements Streamable {
    public String[] value;

    public ContextIdSeqHolder() {
    }

    public ContextIdSeqHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ContextIdSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ContextIdSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ContextIdSeqHelper.write(out, this.value);
    }
}

