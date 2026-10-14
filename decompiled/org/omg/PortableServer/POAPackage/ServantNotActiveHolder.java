/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.ServantNotActiveHelper;

public final class ServantNotActiveHolder
implements Streamable {
    public ServantNotActive value;

    public ServantNotActiveHolder() {
    }

    public ServantNotActiveHolder(ServantNotActive initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServantNotActiveHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ServantNotActiveHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServantNotActiveHelper.write(_out, this.value);
    }
}

