/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.UnsupportedProperty;
import org.omg.PortableGroup.UnsupportedPropertyHelper;

public final class UnsupportedPropertyHolder
implements Streamable {
    public UnsupportedProperty value;

    public UnsupportedPropertyHolder() {
    }

    public UnsupportedPropertyHolder(UnsupportedProperty initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UnsupportedPropertyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = UnsupportedPropertyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        UnsupportedPropertyHelper.write(_out, this.value);
    }
}

