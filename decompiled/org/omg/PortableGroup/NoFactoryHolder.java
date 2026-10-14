/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.NoFactoryHelper;

public final class NoFactoryHolder
implements Streamable {
    public NoFactory value;

    public NoFactoryHolder() {
    }

    public NoFactoryHolder(NoFactory initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NoFactoryHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NoFactoryHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NoFactoryHelper.write(_out, this.value);
    }
}

