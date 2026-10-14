/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AuditEventType;
import org.omg.Security.AuditEventTypeHelper;

public final class AuditEventTypeHolder
implements Streamable {
    public AuditEventType value;

    public AuditEventTypeHolder() {
    }

    public AuditEventTypeHolder(AuditEventType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuditEventTypeHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AuditEventTypeHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AuditEventTypeHelper.write(_out, this.value);
    }
}

