/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.NameValuePair;
import org.omg.DynamicAny.NameValuePairHelper;

public final class NameValuePairHolder
implements Streamable {
    public NameValuePair value;

    public NameValuePairHolder() {
    }

    public NameValuePairHolder(NameValuePair initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NameValuePairHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NameValuePairHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NameValuePairHelper.write(_out, this.value);
    }
}

