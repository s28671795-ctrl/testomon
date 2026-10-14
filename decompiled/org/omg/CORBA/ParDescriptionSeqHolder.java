/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ParDescriptionSeqHelper;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ParDescriptionSeqHolder
implements Streamable {
    public ParameterDescription[] value;

    public ParDescriptionSeqHolder() {
    }

    public ParDescriptionSeqHolder(ParameterDescription[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ParDescriptionSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ParDescriptionSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ParDescriptionSeqHelper.write(out, this.value);
    }
}

