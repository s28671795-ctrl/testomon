/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ServiceInformation;
import org.omg.CORBA.ServiceInformationHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ServiceInformationHolder
implements Streamable {
    public ServiceInformation value;

    public ServiceInformationHolder() {
    }

    public ServiceInformationHolder(ServiceInformation initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceInformationHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ServiceInformationHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServiceInformationHelper.write(_out, this.value);
    }
}

