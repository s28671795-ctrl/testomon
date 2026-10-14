/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ExtValueDefPackage;

import org.omg.CORBA.ExtValueDefPackage.ExtFullValueDescription;
import org.omg.CORBA.ExtValueDefPackage.ExtFullValueDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtFullValueDescriptionHolder
implements Streamable {
    public ExtFullValueDescription value;

    public ExtFullValueDescriptionHolder() {
    }

    public ExtFullValueDescriptionHolder(ExtFullValueDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtFullValueDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ExtFullValueDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtFullValueDescriptionHelper.write(_out, this.value);
    }
}

