/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel1;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel1.Current;
import org.omg.SecurityLevel1.CurrentHelper;

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

