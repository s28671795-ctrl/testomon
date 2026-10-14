/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.MechandOptions;
import org.omg.Security.MechandOptionsListHelper;

public final class MechandOptionsListHolder
implements Streamable {
    public MechandOptions[] value;

    public MechandOptionsListHolder() {
    }

    public MechandOptionsListHolder(MechandOptions[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MechandOptionsListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MechandOptionsListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        MechandOptionsListHelper.write(out, this.value);
    }
}

