/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PrimitiveKind;
import org.omg.CORBA.PrimitiveKindHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PrimitiveKindHolder
implements Streamable {
    public PrimitiveKind value;

    public PrimitiveKindHolder() {
    }

    public PrimitiveKindHolder(PrimitiveKind initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PrimitiveKindHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PrimitiveKindHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PrimitiveKindHelper.write(out, this.value);
    }
}

