/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Current;
import org.omg.CORBA.CurrentHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CurrentHolder
implements Streamable {
    public Current value;

    public CurrentHolder() {
    }

    public CurrentHolder(Current initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CurrentHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CurrentHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        CurrentHelper.write(_out, this.value);
    }
}

