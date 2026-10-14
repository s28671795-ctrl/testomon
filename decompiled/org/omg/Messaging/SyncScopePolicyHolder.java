/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.SyncScopePolicy;
import org.omg.Messaging.SyncScopePolicyHelper;

public final class SyncScopePolicyHolder
implements Streamable {
    public SyncScopePolicy value;

    public SyncScopePolicyHolder() {
    }

    public SyncScopePolicyHolder(SyncScopePolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SyncScopePolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SyncScopePolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        SyncScopePolicyHelper.write(_out, this.value);
    }
}

