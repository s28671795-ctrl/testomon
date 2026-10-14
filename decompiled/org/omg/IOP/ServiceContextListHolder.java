/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IOP.ServiceContext;
import org.omg.IOP.ServiceContextListHelper;

public final class ServiceContextListHolder
implements Streamable {
    public ServiceContext[] value;

    public ServiceContextListHolder() {
    }

    public ServiceContextListHolder(ServiceContext[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceContextListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServiceContextListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ServiceContextListHelper.write(out, this.value);
    }
}

