/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.FactoryInfo;
import org.omg.PortableGroup.FactoryInfosHelper;

public final class FactoryInfosHolder
implements Streamable {
    public FactoryInfo[] value;

    public FactoryInfosHolder() {
    }

    public FactoryInfosHolder(FactoryInfo[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FactoryInfosHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = FactoryInfosHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        FactoryInfosHelper.write(out, this.value);
    }
}

