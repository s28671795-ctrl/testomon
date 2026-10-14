/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActiveHelper;

public final class ServantAlreadyActiveHolder
implements Streamable {
    public ServantAlreadyActive value;

    public ServantAlreadyActiveHolder() {
    }

    public ServantAlreadyActiveHolder(ServantAlreadyActive initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServantAlreadyActiveHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ServantAlreadyActiveHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServantAlreadyActiveHelper.write(_out, this.value);
    }
}

