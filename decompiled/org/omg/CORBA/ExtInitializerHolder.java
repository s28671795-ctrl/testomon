/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtInitializerHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtInitializerHolder
implements Streamable {
    public ExtInitializer value;

    public ExtInitializerHolder() {
    }

    public ExtInitializerHolder(ExtInitializer initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtInitializerHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ExtInitializerHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtInitializerHelper.write(_out, this.value);
    }
}

