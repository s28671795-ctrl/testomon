/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext.CodeBasePackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SendingContext.CodeBasePackage.URLSeqHelper;

public final class URLSeqHolder
implements Streamable {
    public String[] value;

    public URLSeqHolder() {
    }

    public URLSeqHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return URLSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = URLSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        URLSeqHelper.write(out, this.value);
    }
}

