/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AuditEventType;
import org.omg.Security.AuditEventTypeListHelper;

public final class AuditEventTypeListHolder
implements Streamable {
    public AuditEventType[] value;

    public AuditEventTypeListHolder() {
    }

    public AuditEventTypeListHolder(AuditEventType[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuditEventTypeListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuditEventTypeListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AuditEventTypeListHelper.write(out, this.value);
    }
}

