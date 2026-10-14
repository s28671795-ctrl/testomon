/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.ServiceSpecificNameHelper;

public final class ServiceSpecificNameHolder
implements Streamable {
    public byte[] value;

    public ServiceSpecificNameHolder() {
    }

    public ServiceSpecificNameHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceSpecificNameHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServiceSpecificNameHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ServiceSpecificNameHelper.write(out, this.value);
    }
}

