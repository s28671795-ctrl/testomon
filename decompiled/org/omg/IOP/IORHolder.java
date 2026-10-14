/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.IOR;
import org.omg.IOP.IORHelper;

public final class IORHolder
implements Streamable {
    public IOR value;

    public IORHolder() {
    }

    public IORHolder(IOR initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IORHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = IORHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        IORHelper.write(_out, this.value);
    }
}

