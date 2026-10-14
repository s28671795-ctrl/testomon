/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.InvalidPropertyHelper;

public final class InvalidPropertyHolder
implements Streamable {
    public InvalidProperty value;

    public InvalidPropertyHolder() {
    }

    public InvalidPropertyHolder(InvalidProperty initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidPropertyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidPropertyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidPropertyHelper.write(_out, this.value);
    }
}

