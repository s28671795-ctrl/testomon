/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.TypeCodePackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.BadKindHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class BadKindHolder
implements Streamable {
    public BadKind value;

    public BadKindHolder() {
    }

    public BadKindHolder(BadKind initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BadKindHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = BadKindHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        BadKindHelper.write(_out, this.value);
    }
}

