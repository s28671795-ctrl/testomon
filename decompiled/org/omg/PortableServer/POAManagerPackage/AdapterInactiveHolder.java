/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAManagerPackage.AdapterInactiveHelper;

public final class AdapterInactiveHolder
implements Streamable {
    public AdapterInactive value;

    public AdapterInactiveHolder() {
    }

    public AdapterInactiveHolder(AdapterInactive initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AdapterInactiveHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AdapterInactiveHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AdapterInactiveHelper.write(_out, this.value);
    }
}

