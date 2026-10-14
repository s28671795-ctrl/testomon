/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.InterfaceNotFound;
import org.omg.PortableGroup.InterfaceNotFoundHelper;

public final class InterfaceNotFoundHolder
implements Streamable {
    public InterfaceNotFound value;

    public InterfaceNotFoundHolder() {
    }

    public InterfaceNotFoundHolder(InterfaceNotFound initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InterfaceNotFoundHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InterfaceNotFoundHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InterfaceNotFoundHelper.write(_out, this.value);
    }
}

