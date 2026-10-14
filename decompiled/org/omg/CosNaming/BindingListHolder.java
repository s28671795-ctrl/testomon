/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingListHelper;

public final class BindingListHolder
implements Streamable {
    public Binding[] value;

    public BindingListHolder() {
    }

    public BindingListHolder(Binding[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BindingListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = BindingListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        BindingListHelper.write(out, this.value);
    }
}

