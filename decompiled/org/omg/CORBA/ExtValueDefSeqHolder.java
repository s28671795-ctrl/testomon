/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.ExtValueDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtValueDefSeqHolder
implements Streamable {
    public ExtValueDef[] value;

    public ExtValueDefSeqHolder() {
    }

    public ExtValueDefSeqHolder(ExtValueDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtValueDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtValueDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExtValueDefSeqHelper.write(out, this.value);
    }
}

