/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class BooleanHolder
implements Streamable {
    public boolean value;

    public BooleanHolder() {
    }

    public BooleanHolder(boolean initial) {
        this.value = initial;
    }

    @Override
    public void _read(InputStream is) {
        this.value = is.read_boolean();
    }

    @Override
    public void _write(OutputStream os) {
        os.write_boolean(this.value);
    }

    @Override
    public TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_boolean);
    }
}

