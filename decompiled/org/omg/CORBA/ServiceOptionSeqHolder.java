/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ServiceOptionSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ServiceOptionSeqHolder
implements Streamable {
    public int[] value;

    public ServiceOptionSeqHolder() {
    }

    public ServiceOptionSeqHolder(int[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServiceOptionSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServiceOptionSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ServiceOptionSeqHelper.write(out, this.value);
    }
}

