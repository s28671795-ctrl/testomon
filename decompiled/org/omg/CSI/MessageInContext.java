/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.portable.IDLEntity;

public final class MessageInContext
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public long client_context_id;
    public boolean discard_context;

    public MessageInContext() {
    }

    public MessageInContext(long client_context_id, boolean discard_context) {
        this.client_context_id = client_context_id;
        this.discard_context = discard_context;
    }
}

