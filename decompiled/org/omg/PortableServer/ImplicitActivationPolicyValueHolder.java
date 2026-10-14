/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicyValueHelper;

public final class ImplicitActivationPolicyValueHolder
implements Streamable {
    public ImplicitActivationPolicyValue value;

    public ImplicitActivationPolicyValueHolder() {
    }

    public ImplicitActivationPolicyValueHolder(ImplicitActivationPolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ImplicitActivationPolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ImplicitActivationPolicyValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ImplicitActivationPolicyValueHelper.write(out, this.value);
    }
}

