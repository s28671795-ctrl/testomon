/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.CompoundSecMech;
import org.omg.CSIIOP.CompoundSecMechanismsHelper;

public final class CompoundSecMechanismsHolder
implements Streamable {
    public CompoundSecMech[] value;

    public CompoundSecMechanismsHolder() {
    }

    public CompoundSecMechanismsHolder(CompoundSecMech[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CompoundSecMechanismsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = CompoundSecMechanismsHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        CompoundSecMechanismsHelper.write(out, this.value);
    }
}

