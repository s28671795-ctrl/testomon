/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.Property;
import org.omg.PortableGroup.PropertyHelper;

public final class PropertyHolder
implements Streamable {
    public Property value;

    public PropertyHolder() {
    }

    public PropertyHolder(Property initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PropertyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = PropertyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        PropertyHelper.write(_out, this.value);
    }
}

