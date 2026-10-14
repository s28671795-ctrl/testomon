/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynValueBox;
import org.omg.DynamicAny.DynValueBoxHelper;

public final class DynValueBoxHolder
implements Streamable {
    public DynValueBox value;

    public DynValueBoxHolder() {
    }

    public DynValueBoxHolder(DynValueBox initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynValueBoxHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynValueBoxHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynValueBoxHelper.write(_out, this.value);
    }
}

