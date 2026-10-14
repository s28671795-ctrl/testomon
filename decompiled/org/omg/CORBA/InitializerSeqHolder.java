/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Initializer;
import org.omg.CORBA.InitializerSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InitializerSeqHolder
implements Streamable {
    public Initializer[] value;

    public InitializerSeqHolder() {
    }

    public InitializerSeqHolder(Initializer[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InitializerSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = InitializerSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        InitializerSeqHelper.write(out, this.value);
    }
}

