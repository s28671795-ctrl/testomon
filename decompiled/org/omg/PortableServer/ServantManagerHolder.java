/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ServantManager;
import org.omg.PortableServer.ServantManagerHelper;

public final class ServantManagerHolder
implements Streamable {
    public ServantManager value;

    public ServantManagerHolder() {
    }

    public ServantManagerHolder(ServantManager initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServantManagerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServantManagerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServantManagerHelper.write(_out, this.value);
    }
}

