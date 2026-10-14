/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.OperationDescription;
import org.omg.CORBA.OperationDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class OperationDescriptionHolder
implements Streamable {
    public OperationDescription value;

    public OperationDescriptionHolder() {
    }

    public OperationDescriptionHolder(OperationDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OperationDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = OperationDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        OperationDescriptionHelper.write(_out, this.value);
    }
}

