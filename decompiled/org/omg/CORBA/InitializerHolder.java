/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Initializer;
import org.omg.CORBA.InitializerHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InitializerHolder
implements Streamable {
    public Initializer value;

    public InitializerHolder() {
    }

    public InitializerHolder(Initializer initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InitializerHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InitializerHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InitializerHelper.write(_out, this.value);
    }
}

