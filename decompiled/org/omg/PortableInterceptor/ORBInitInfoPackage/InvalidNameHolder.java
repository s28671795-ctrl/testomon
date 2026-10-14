/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor.ORBInitInfoPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ORBInitInfoPackage.InvalidName;
import org.omg.PortableInterceptor.ORBInitInfoPackage.InvalidNameHelper;

public final class InvalidNameHolder
implements Streamable {
    public InvalidName value;

    public InvalidNameHolder() {
    }

    public InvalidNameHolder(InvalidName initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidNameHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidNameHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidNameHelper.write(_out, this.value);
    }
}

