/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.Serializable;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueBaseHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueBaseHolder
implements Streamable {
    public Serializable value;

    public ValueBaseHolder() {
    }

    public ValueBaseHolder(Serializable initial) {
        this.value = initial;
    }

    @Override
    public void _read(InputStream is) {
        this.value = ValueBaseHelper.read(is);
    }

    @Override
    public void _write(OutputStream os) {
        ValueBaseHelper.write(os, this.value);
    }

    @Override
    public TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_value);
    }
}

