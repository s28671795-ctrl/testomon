/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynValueCommon;
import org.omg.DynamicAny.DynValueCommonHelper;

public final class DynValueCommonHolder
implements Streamable {
    public DynValueCommon value;

    public DynValueCommonHolder() {
    }

    public DynValueCommonHolder(DynValueCommon initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DynValueCommonHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DynValueCommonHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DynValueCommonHelper.write(_out, this.value);
    }
}

