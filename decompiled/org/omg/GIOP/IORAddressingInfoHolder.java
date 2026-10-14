/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.IORAddressingInfo;
import org.omg.GIOP.IORAddressingInfoHelper;

public final class IORAddressingInfoHolder
implements Streamable {
    public IORAddressingInfo value;

    public IORAddressingInfoHolder() {
    }

    public IORAddressingInfoHolder(IORAddressingInfo initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IORAddressingInfoHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = IORAddressingInfoHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        IORAddressingInfoHelper.write(_out, this.value);
    }
}

