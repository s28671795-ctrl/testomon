/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.ServiceContext;
import org.omg.IOP.ServiceContextHelper;

public final class ServiceContextHolder
implements Streamable {
    public ServiceContext value;

    public ServiceContextHolder() {
    }

    public ServiceContextHolder(ServiceContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceContextHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ServiceContextHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServiceContextHelper.write(_out, this.value);
    }
}

