/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ConstantDescription;
import org.omg.CORBA.ConstantDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ConstantDescriptionHolder
implements Streamable {
    public ConstantDescription value;

    public ConstantDescriptionHolder() {
    }

    public ConstantDescriptionHolder(ConstantDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ConstantDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ConstantDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ConstantDescriptionHelper.write(_out, this.value);
    }
}

