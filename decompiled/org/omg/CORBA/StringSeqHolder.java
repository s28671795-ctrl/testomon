/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.StringSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class StringSeqHolder
implements Streamable {
    public String[] value;

    public StringSeqHolder() {
    }

    public StringSeqHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return StringSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = StringSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        StringSeqHelper.write(out, this.value);
    }
}

