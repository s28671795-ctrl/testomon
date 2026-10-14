/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ContainedPackage;

import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DescriptionHolder
implements Streamable {
    public Description value;

    public DescriptionHolder() {
    }

    public DescriptionHolder(Description initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = DescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        DescriptionHelper.write(_out, this.value);
    }
}

