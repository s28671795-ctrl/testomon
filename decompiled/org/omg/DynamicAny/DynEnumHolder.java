/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynEnum;
import org.omg.DynamicAny.DynEnumHelper;

public final class DynEnumHolder
implements Streamable {
    public DynEnum value;

    public DynEnumHolder() {
    }

    public DynEnumHolder(DynEnum initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynEnumHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynEnumHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynEnumHelper.write(_out, this.value);
    }
}

