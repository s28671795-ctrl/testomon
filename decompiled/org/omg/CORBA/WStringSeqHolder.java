/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WStringSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class WStringSeqHolder
implements Streamable {
    public String[] value;

    public WStringSeqHolder() {
    }

    public WStringSeqHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return WStringSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = WStringSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        WStringSeqHelper.write(out, this.value);
    }
}

