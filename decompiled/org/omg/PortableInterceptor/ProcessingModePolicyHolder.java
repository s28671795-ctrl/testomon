/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableInterceptor.ProcessingModePolicy;
import org.omg.PortableInterceptor.ProcessingModePolicyHelper;

public final class ProcessingModePolicyHolder
implements Streamable {
    public ProcessingModePolicy value;

    public ProcessingModePolicyHolder() {
    }

    public ProcessingModePolicyHolder(ProcessingModePolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ProcessingModePolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ProcessingModePolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ProcessingModePolicyHelper.write(_out, this.value);
    }
}

