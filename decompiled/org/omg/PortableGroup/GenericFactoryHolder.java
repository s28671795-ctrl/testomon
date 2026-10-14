/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.GenericFactory;
import org.omg.PortableGroup.GenericFactoryHelper;

public final class GenericFactoryHolder
implements Streamable {
    public GenericFactory value;

    public GenericFactoryHolder() {
    }

    public GenericFactoryHolder(GenericFactory initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return GenericFactoryHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = GenericFactoryHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        GenericFactoryHelper.write(_out, this.value);
    }
}

