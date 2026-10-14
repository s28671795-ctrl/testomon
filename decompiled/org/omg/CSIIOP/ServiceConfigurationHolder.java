/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.ServiceConfiguration;
import org.omg.CSIIOP.ServiceConfigurationHelper;

public final class ServiceConfigurationHolder
implements Streamable {
    public ServiceConfiguration value;

    public ServiceConfigurationHolder() {
    }

    public ServiceConfigurationHolder(ServiceConfiguration initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceConfigurationHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ServiceConfigurationHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServiceConfigurationHelper.write(_out, this.value);
    }
}

