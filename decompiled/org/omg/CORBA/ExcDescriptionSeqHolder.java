/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExcDescriptionSeqHelper;
import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExcDescriptionSeqHolder
implements Streamable {
    public ExceptionDescription[] value;

    public ExcDescriptionSeqHolder() {
    }

    public ExcDescriptionSeqHolder(ExceptionDescription[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExcDescriptionSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExcDescriptionSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExcDescriptionSeqHelper.write(out, this.value);
    }
}

