/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.PropertyManager;
import org.omg.PortableGroup.PropertyManagerHelper;

public final class PropertyManagerHolder
implements Streamable {
    public PropertyManager value;

    public PropertyManagerHolder() {
    }

    public PropertyManagerHolder(PropertyManager initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PropertyManagerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PropertyManagerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PropertyManagerHelper.write(_out, this.value);
    }
}

