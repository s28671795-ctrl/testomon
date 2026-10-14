/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor.ORBInitInfoPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateNameHelper;

public final class DuplicateNameHolder
implements Streamable {
    public DuplicateName value;

    public DuplicateNameHolder() {
    }

    public DuplicateNameHolder(DuplicateName initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DuplicateNameHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = DuplicateNameHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        DuplicateNameHelper.write(_out, this.value);
    }
}

