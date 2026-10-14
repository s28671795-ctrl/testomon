/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.MechandOptions;
import org.omg.Security.MechandOptionsHelper;

public final class MechandOptionsHolder
implements Streamable {
    public MechandOptions value;

    public MechandOptionsHolder() {
    }

    public MechandOptionsHolder(MechandOptions initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MechandOptionsHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = MechandOptionsHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        MechandOptionsHelper.write(_out, this.value);
    }
}

