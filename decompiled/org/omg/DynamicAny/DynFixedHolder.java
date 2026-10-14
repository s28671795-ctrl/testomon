/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynFixed;
import org.omg.DynamicAny.DynFixedHelper;

public final class DynFixedHolder
implements Streamable {
    public DynFixed value;

    public DynFixedHolder() {
    }

    public DynFixedHolder(DynFixed initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynFixedHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynFixedHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynFixedHelper.write(_out, this.value);
    }
}

