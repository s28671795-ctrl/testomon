/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.InterfaceAttrExtensionPackage;

import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescription;
import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtFullInterfaceDescriptionHolder
implements Streamable {
    public ExtFullInterfaceDescription value;

    public ExtFullInterfaceDescriptionHolder() {
    }

    public ExtFullInterfaceDescriptionHolder(ExtFullInterfaceDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtFullInterfaceDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ExtFullInterfaceDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtFullInterfaceDescriptionHelper.write(_out, this.value);
    }
}

