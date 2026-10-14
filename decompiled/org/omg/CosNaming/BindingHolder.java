/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingHelper;

public final class BindingHolder
implements Streamable {
    public Binding value;

    public BindingHolder() {
    }

    public BindingHolder(Binding initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BindingHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = BindingHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        BindingHelper.write(_out, this.value);
    }
}

