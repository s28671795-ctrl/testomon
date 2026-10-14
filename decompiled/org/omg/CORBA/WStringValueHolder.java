/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WStringValueHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class WStringValueHolder
implements Streamable {
    public String value;

    public WStringValueHolder() {
    }

    public WStringValueHolder(String initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return WStringValueHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = WStringValueHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        WStringValueHelper.write(_out, this.value);
    }
}

