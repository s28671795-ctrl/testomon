/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.OpDescriptionSeqHelper;
import org.omg.CORBA.OperationDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class OpDescriptionSeqHolder
implements Streamable {
    public OperationDescription[] value;

    public OpDescriptionSeqHolder() {
    }

    public OpDescriptionSeqHolder(OperationDescription[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OpDescriptionSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OpDescriptionSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        OpDescriptionSeqHelper.write(out, this.value);
    }
}

