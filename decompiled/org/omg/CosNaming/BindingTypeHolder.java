/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.BindingTypeHelper;

public final class BindingTypeHolder
implements Streamable {
    public BindingType value;

    public BindingTypeHolder() {
    }

    public BindingTypeHolder(BindingType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BindingTypeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = BindingTypeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        BindingTypeHelper.write(out, this.value);
    }
}

