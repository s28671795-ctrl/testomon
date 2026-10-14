/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameHelper;

public final class NameHolder
implements Streamable {
    public NameComponent[] value;

    public NameHolder() {
    }

    public NameHolder(NameComponent[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NameHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = NameHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        NameHelper.write(out, this.value);
    }
}

