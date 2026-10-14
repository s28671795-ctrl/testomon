/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ServantActivator;
import org.omg.PortableServer.ServantActivatorHelper;

public final class ServantActivatorHolder
implements Streamable {
    public ServantActivator value;

    public ServantActivatorHolder() {
    }

    public ServantActivatorHolder(ServantActivator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServantActivatorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServantActivatorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServantActivatorHelper.write(_out, this.value);
    }
}

