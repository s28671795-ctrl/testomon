/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.AdapterActivator;
import org.omg.PortableServer.AdapterActivatorHelper;

public final class AdapterActivatorHolder
implements Streamable {
    public AdapterActivator value;

    public AdapterActivatorHolder() {
    }

    public AdapterActivatorHolder(AdapterActivator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AdapterActivatorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AdapterActivatorHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AdapterActivatorHelper.write(_out, this.value);
    }
}

