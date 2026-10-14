/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtInitializerSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtInitializerSeqHolder
implements Streamable {
    public ExtInitializer[] value;

    public ExtInitializerSeqHolder() {
    }

    public ExtInitializerSeqHolder(ExtInitializer[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtInitializerSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtInitializerSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExtInitializerSeqHelper.write(out, this.value);
    }
}

