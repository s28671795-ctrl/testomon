/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA.RTORBPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.RTORBPackage.InvalidThreadpool;
import org.omg.RTCORBA.RTORBPackage.InvalidThreadpoolHelper;

public final class InvalidThreadpoolHolder
implements Streamable {
    public InvalidThreadpool value;

    public InvalidThreadpoolHolder() {
    }

    public InvalidThreadpoolHolder(InvalidThreadpool initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidThreadpoolHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidThreadpoolHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidThreadpoolHelper.write(_out, this.value);
    }
}

