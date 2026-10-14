/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.BindingIterator;
import org.omg.CosNaming.BindingIteratorHelper;

public final class BindingIteratorHolder
implements Streamable {
    public BindingIterator value;

    public BindingIteratorHolder() {
    }

    public BindingIteratorHolder(BindingIterator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BindingIteratorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = BindingIteratorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        BindingIteratorHelper.write(_out, this.value);
    }
}

