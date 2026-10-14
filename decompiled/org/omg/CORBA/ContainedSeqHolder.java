/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Contained;
import org.omg.CORBA.ContainedSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ContainedSeqHolder
implements Streamable {
    public Contained[] value;

    public ContainedSeqHolder() {
    }

    public ContainedSeqHolder(Contained[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ContainedSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ContainedSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ContainedSeqHelper.write(out, this.value);
    }
}

