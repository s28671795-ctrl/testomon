/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.NoServantHelper;

public final class NoServantHolder
implements Streamable {
    public NoServant value;

    public NoServantHolder() {
    }

    public NoServantHolder(NoServant initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NoServantHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NoServantHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NoServantHelper.write(_out, this.value);
    }
}

