/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDef;
import org.omg.CORBA.ValueDefSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueDefSeqHolder
implements Streamable {
    public ValueDef[] value;

    public ValueDefSeqHolder() {
    }

    public ValueDefSeqHolder(ValueDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ValueDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ValueDefSeqHelper.write(out, this.value);
    }
}

