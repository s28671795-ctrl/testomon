/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.InvalidValueHelper;

public final class InvalidValueHolder
implements Streamable {
    public InvalidValue value;

    public InvalidValueHolder() {
    }

    public InvalidValueHolder(InvalidValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidValueHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidValueHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidValueHelper.write(_out, this.value);
    }
}

