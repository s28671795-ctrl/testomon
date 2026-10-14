/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.CompoundSecMechList;
import org.omg.CSIIOP.CompoundSecMechListHelper;

public final class CompoundSecMechListHolder
implements Streamable {
    public CompoundSecMechList value;

    public CompoundSecMechListHolder() {
    }

    public CompoundSecMechListHolder(CompoundSecMechList initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CompoundSecMechListHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CompoundSecMechListHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CompoundSecMechListHelper.write(_out, this.value);
    }
}

