/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.PortableInterceptor.InvalidSlotHelper;

public final class InvalidSlotHolder
implements Streamable {
    public InvalidSlot value;

    public InvalidSlotHolder() {
    }

    public InvalidSlotHolder(InvalidSlot initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidSlotHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidSlotHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidSlotHelper.write(_out, this.value);
    }
}

