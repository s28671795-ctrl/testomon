/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyHelper;

public final class ImplicitActivationPolicyHolder
implements Streamable {
    public ImplicitActivationPolicy value;

    public ImplicitActivationPolicyHolder() {
    }

    public ImplicitActivationPolicyHolder(ImplicitActivationPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ImplicitActivationPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ImplicitActivationPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ImplicitActivationPolicyHelper.write(_out, this.value);
    }
}

