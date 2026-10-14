/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.Right;
import org.omg.Security.RightHelper;

public final class RightHolder
implements Streamable {
    public Right value;

    public RightHolder() {
    }

    public RightHolder(Right initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RightHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = RightHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        RightHelper.write(_out, this.value);
    }
}

