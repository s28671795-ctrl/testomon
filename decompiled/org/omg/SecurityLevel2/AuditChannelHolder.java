/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.AuditChannel;
import org.omg.SecurityLevel2.AuditChannelHelper;

public final class AuditChannelHolder
implements Streamable {
    public AuditChannel value;

    public AuditChannelHolder() {
    }

    public AuditChannelHolder(AuditChannel initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuditChannelHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuditChannelHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AuditChannelHelper.write(_out, this.value);
    }
}

