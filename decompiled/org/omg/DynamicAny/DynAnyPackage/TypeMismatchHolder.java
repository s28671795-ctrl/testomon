/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatchHelper;

public final class TypeMismatchHolder
implements Streamable {
    public TypeMismatch value;

    public TypeMismatchHolder() {
    }

    public TypeMismatchHolder(TypeMismatch initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TypeMismatchHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TypeMismatchHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TypeMismatchHelper.write(_out, this.value);
    }
}

