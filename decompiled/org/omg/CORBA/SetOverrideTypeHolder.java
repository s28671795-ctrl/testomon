/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.SetOverrideTypeHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SetOverrideTypeHolder
implements Streamable {
    public SetOverrideType value;

    public SetOverrideTypeHolder() {
    }

    public SetOverrideTypeHolder(SetOverrideType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SetOverrideTypeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SetOverrideTypeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        SetOverrideTypeHelper.write(out, this.value);
    }
}

