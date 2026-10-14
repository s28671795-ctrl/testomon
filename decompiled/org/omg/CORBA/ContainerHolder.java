/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ContainerHolder
implements Streamable {
    public Container value;

    public ContainerHolder() {
    }

    public ContainerHolder(Container initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ContainerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ContainerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ContainerHelper.write(_out, this.value);
    }
}

