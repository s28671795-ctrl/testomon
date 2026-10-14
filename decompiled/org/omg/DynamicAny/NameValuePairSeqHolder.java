/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.NameValuePair;
import org.omg.DynamicAny.NameValuePairSeqHelper;

public final class NameValuePairSeqHolder
implements Streamable {
    public NameValuePair[] value;

    public NameValuePairSeqHolder() {
    }

    public NameValuePairSeqHolder(NameValuePair[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NameValuePairSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = NameValuePairSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        NameValuePairSeqHelper.write(out, this.value);
    }
}

