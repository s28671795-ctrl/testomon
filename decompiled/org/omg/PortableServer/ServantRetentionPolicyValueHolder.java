/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer.ServantRetentionPolicyValueHelper;

public final class ServantRetentionPolicyValueHolder
implements Streamable {
    public ServantRetentionPolicyValue value;

    public ServantRetentionPolicyValueHolder() {
    }

    public ServantRetentionPolicyValueHolder(ServantRetentionPolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServantRetentionPolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServantRetentionPolicyValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ServantRetentionPolicyValueHelper.write(out, this.value);
    }
}

