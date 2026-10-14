/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.MechanismTypeListHelper;

public final class MechanismTypeListHolder
implements Streamable {
    public String[] value;

    public MechanismTypeListHolder() {
    }

    public MechanismTypeListHolder(String[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MechanismTypeListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MechanismTypeListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        MechanismTypeListHelper.write(out, this.value);
    }
}

