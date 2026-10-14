/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.AuditDecision;
import org.omg.SecurityLevel2.AuditDecisionHelper;

public final class AuditDecisionHolder
implements Streamable {
    public AuditDecision value;

    public AuditDecisionHolder() {
    }

    public AuditDecisionHolder(AuditDecision initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuditDecisionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuditDecisionHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AuditDecisionHelper.write(_out, this.value);
    }
}

