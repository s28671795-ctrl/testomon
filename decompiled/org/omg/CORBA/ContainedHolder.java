/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Contained;
import org.omg.CORBA.ContainedHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ContainedHolder
implements Streamable {
    public Contained value;

    public ContainedHolder() {
    }

    public ContainedHolder(Contained initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ContainedHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ContainedHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ContainedHelper.write(_out, this.value);
    }
}

