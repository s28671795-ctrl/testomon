/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.Property;

public final class PropertiesHolder
implements Streamable {
    public Property[] value;

    public PropertiesHolder() {
    }

    public PropertiesHolder(Property[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PropertiesHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PropertiesHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PropertiesHelper.write(out, this.value);
    }
}

