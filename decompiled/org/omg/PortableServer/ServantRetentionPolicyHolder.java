/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyHelper;

public final class ServantRetentionPolicyHolder
implements Streamable {
    public ServantRetentionPolicy value;

    public ServantRetentionPolicyHolder() {
    }

    public ServantRetentionPolicyHolder(ServantRetentionPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ServantRetentionPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ServantRetentionPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ServantRetentionPolicyHelper.write(_out, this.value);
    }
}

