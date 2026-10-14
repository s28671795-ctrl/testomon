/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SelectorValue;
import org.omg.Security.SelectorValueListHelper;

public final class SelectorValueListHolder
implements Streamable {
    public SelectorValue[] value;

    public SelectorValueListHolder() {
    }

    public SelectorValueListHolder(SelectorValue[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SelectorValueListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SelectorValueListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        SelectorValueListHelper.write(out, this.value);
    }
}

