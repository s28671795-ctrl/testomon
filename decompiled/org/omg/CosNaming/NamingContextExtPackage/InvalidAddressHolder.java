/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextExtPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper;

public final class InvalidAddressHolder
implements Streamable {
    public InvalidAddress value;

    public InvalidAddressHolder() {
    }

    public InvalidAddressHolder(InvalidAddress initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidAddressHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidAddressHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidAddressHelper.write(_out, this.value);
    }
}

