/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityAdmin.AuditPolicy;
import org.omg.SecurityAdmin.AuditPolicyHelper;

public final class AuditPolicyHolder
implements Streamable {
    public AuditPolicy value;

    public AuditPolicyHolder() {
    }

    public AuditPolicyHolder(AuditPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuditPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuditPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AuditPolicyHelper.write(_out, this.value);
    }
}

