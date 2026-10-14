/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.ETF.ObjectKeyHelper;

public final class ObjectKeyHolder
implements Streamable {
    public byte[] value;

    public ObjectKeyHolder() {
    }

    public ObjectKeyHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ObjectKeyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ObjectKeyHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ObjectKeyHelper.write(out, this.value);
    }
}

