/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.SASContextBody;
import org.omg.CSI.SASContextBodyHelper;

public final class SASContextBodyHolder
implements Streamable {
    public SASContextBody value;

    public SASContextBodyHolder() {
    }

    public SASContextBodyHolder(SASContextBody initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SASContextBodyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SASContextBodyHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        SASContextBodyHelper.write(out, this.value);
    }
}

