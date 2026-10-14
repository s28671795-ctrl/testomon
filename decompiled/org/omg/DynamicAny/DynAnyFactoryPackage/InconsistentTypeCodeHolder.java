/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyFactoryPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCodeHelper;

public final class InconsistentTypeCodeHolder
implements Streamable {
    public InconsistentTypeCode value;

    public InconsistentTypeCodeHolder() {
    }

    public InconsistentTypeCodeHolder(InconsistentTypeCode initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InconsistentTypeCodeHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InconsistentTypeCodeHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InconsistentTypeCodeHelper.write(_out, this.value);
    }
}

