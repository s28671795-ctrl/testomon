/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableGroup.LocationsHelper;

public final class LocationsHolder
implements Streamable {
    public NameComponent[][] value;

    public LocationsHolder() {
    }

    public LocationsHolder(NameComponent[][] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocationsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LocationsHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LocationsHelper.write(out, this.value);
    }
}

