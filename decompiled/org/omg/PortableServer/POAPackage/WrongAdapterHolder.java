/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongAdapterHelper;

public final class WrongAdapterHolder
implements Streamable {
    public WrongAdapter value;

    public WrongAdapterHolder() {
    }

    public WrongAdapterHolder(WrongAdapter initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return WrongAdapterHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = WrongAdapterHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        WrongAdapterHelper.write(_out, this.value);
    }
}

