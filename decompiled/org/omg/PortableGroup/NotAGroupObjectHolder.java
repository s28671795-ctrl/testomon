/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.NotAGroupObject;
import org.omg.PortableGroup.NotAGroupObjectHelper;

public final class NotAGroupObjectHolder
implements Streamable {
    public NotAGroupObject value;

    public NotAGroupObjectHolder() {
    }

    public NotAGroupObjectHolder(NotAGroupObject initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NotAGroupObjectHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NotAGroupObjectHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NotAGroupObjectHelper.write(_out, this.value);
    }
}

