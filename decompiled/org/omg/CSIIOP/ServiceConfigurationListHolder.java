/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.ServiceConfiguration;
import org.omg.CSIIOP.ServiceConfigurationListHelper;

public final class ServiceConfigurationListHolder
implements Streamable {
    public ServiceConfiguration[] value;

    public ServiceConfigurationListHolder() {
    }

    public ServiceConfigurationListHolder(ServiceConfiguration[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceConfigurationListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServiceConfigurationListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ServiceConfigurationListHelper.write(out, this.value);
    }
}

