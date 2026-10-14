/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.InterfaceDefPackage;

import org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription;
import org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class FullInterfaceDescriptionHolder
implements Streamable {
    public FullInterfaceDescription value;

    public FullInterfaceDescriptionHolder() {
    }

    public FullInterfaceDescriptionHolder(FullInterfaceDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FullInterfaceDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = FullInterfaceDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        FullInterfaceDescriptionHelper.write(_out, this.value);
    }
}

