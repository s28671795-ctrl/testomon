/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ValueDefPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class FullValueDescriptionHolder
implements Streamable {
    public FullValueDescription value;

    public FullValueDescriptionHolder() {
    }

    public FullValueDescriptionHolder(FullValueDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return FullValueDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = FullValueDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        FullValueDescriptionHelper.write(_out, this.value);
    }
}

