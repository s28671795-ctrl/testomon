/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ServiceDetail;
import org.omg.CORBA.ServiceDetailHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ServiceDetailHolder
implements Streamable {
    public ServiceDetail value;

    public ServiceDetailHolder() {
    }

    public ServiceDetailHolder(ServiceDetail initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceDetailHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ServiceDetailHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServiceDetailHelper.write(_out, this.value);
    }
}

