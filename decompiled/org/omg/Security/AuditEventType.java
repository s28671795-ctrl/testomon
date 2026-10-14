/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.Security.ExtensibleFamily;

public final class AuditEventType
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public ExtensibleFamily event_family;
    public short event_type;

    public AuditEventType() {
    }

    public AuditEventType(ExtensibleFamily event_family, short event_type) {
        this.event_family = event_family;
        this.event_type = event_type;
    }
}

