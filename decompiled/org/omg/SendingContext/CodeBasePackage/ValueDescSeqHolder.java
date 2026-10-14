/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext.CodeBasePackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;

public final class ValueDescSeqHolder
implements Streamable {
    public FullValueDescription[] value;

    public ValueDescSeqHolder() {
    }

    public ValueDescSeqHolder(FullValueDescription[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ValueDescSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ValueDescSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ValueDescSeqHelper.write(out, this.value);
    }
}

