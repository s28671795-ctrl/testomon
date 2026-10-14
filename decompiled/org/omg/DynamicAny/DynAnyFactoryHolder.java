/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynAnyFactory;
import org.omg.DynamicAny.DynAnyFactoryHelper;

public final class DynAnyFactoryHolder
implements Streamable {
    public DynAnyFactory value;

    public DynAnyFactoryHolder() {
    }

    public DynAnyFactoryHolder(DynAnyFactory initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynAnyFactoryHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynAnyFactoryHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynAnyFactoryHelper.write(_out, this.value);
    }
}

