/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExistsHelper;

public final class AdapterAlreadyExistsHolder
implements Streamable {
    public AdapterAlreadyExists value;

    public AdapterAlreadyExistsHolder() {
    }

    public AdapterAlreadyExistsHolder(AdapterAlreadyExists initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AdapterAlreadyExistsHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AdapterAlreadyExistsHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AdapterAlreadyExistsHelper.write(_out, this.value);
    }
}

