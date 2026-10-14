/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SelectorValue;
import org.omg.Security.SelectorValueHelper;

public final class SelectorValueHolder
implements Streamable {
    public SelectorValue value;

    public SelectorValueHolder() {
    }

    public SelectorValueHolder(SelectorValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SelectorValueHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = SelectorValueHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        SelectorValueHelper.write(_out, this.value);
    }
}

