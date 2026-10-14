/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.FactoryInfo;
import org.omg.PortableGroup.FactoryInfoHelper;

public final class FactoryInfoHolder
implements Streamable {
    public FactoryInfo value;

    public FactoryInfoHolder() {
    }

    public FactoryInfoHolder(FactoryInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FactoryInfoHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = FactoryInfoHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        FactoryInfoHelper.write(_out, this.value);
    }
}

