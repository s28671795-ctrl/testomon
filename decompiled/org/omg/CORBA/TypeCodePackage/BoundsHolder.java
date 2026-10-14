/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.TypeCodePackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.TypeCodePackage.BoundsHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class BoundsHolder
implements Streamable {
    public Bounds value;

    public BoundsHolder() {
    }

    public BoundsHolder(Bounds initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BoundsHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = BoundsHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        BoundsHelper.write(_out, this.value);
    }
}

