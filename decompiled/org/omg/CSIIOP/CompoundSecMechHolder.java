/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.CompoundSecMech;
import org.omg.CSIIOP.CompoundSecMechHelper;

public final class CompoundSecMechHolder
implements Streamable {
    public CompoundSecMech value;

    public CompoundSecMechHolder() {
    }

    public CompoundSecMechHolder(CompoundSecMech initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CompoundSecMechHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CompoundSecMechHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CompoundSecMechHelper.write(_out, this.value);
    }
}

