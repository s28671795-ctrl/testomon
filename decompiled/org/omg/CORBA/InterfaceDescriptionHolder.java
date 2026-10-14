/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InterfaceDescription;
import org.omg.CORBA.InterfaceDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InterfaceDescriptionHolder
implements Streamable {
    public InterfaceDescription value;

    public InterfaceDescriptionHolder() {
    }

    public InterfaceDescriptionHolder(InterfaceDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InterfaceDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InterfaceDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InterfaceDescriptionHelper.write(_out, this.value);
    }
}

