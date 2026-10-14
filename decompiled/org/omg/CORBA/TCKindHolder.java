/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TCKind;
import org.omg.CORBA.TCKindHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TCKindHolder
implements Streamable {
    public TCKind value;

    public TCKindHolder() {
    }

    public TCKindHolder(TCKind initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TCKindHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = TCKindHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        TCKindHelper.write(out, this.value);
    }
}

