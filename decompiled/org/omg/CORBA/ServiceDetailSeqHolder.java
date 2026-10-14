/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ServiceDetail;
import org.omg.CORBA.ServiceDetailSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ServiceDetailSeqHolder
implements Streamable {
    public ServiceDetail[] value;

    public ServiceDetailSeqHolder() {
    }

    public ServiceDetailSeqHolder(ServiceDetail[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceDetailSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServiceDetailSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ServiceDetailSeqHelper.write(out, this.value);
    }
}

