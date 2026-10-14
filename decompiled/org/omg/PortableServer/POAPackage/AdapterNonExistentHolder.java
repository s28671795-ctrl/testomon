/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.AdapterNonExistentHelper;

public final class AdapterNonExistentHolder
implements Streamable {
    public AdapterNonExistent value;

    public AdapterNonExistentHolder() {
    }

    public AdapterNonExistentHolder(AdapterNonExistent initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AdapterNonExistentHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AdapterNonExistentHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AdapterNonExistentHelper.write(_out, this.value);
    }
}

