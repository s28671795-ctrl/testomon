/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InterfaceAttrExtension;
import org.omg.CORBA.InterfaceAttrExtensionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InterfaceAttrExtensionHolder
implements Streamable {
    public InterfaceAttrExtension value;

    public InterfaceAttrExtensionHolder() {
    }

    public InterfaceAttrExtensionHolder(InterfaceAttrExtension initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InterfaceAttrExtensionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = InterfaceAttrExtensionHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        InterfaceAttrExtensionHelper.write(_out, this.value);
    }
}

