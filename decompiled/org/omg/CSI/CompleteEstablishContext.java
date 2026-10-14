/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.portable.IDLEntity;

public final class CompleteEstablishContext
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public long client_context_id;
    public boolean context_stateful;
    public byte[] final_context_token;

    public CompleteEstablishContext() {
    }

    public CompleteEstablishContext(long client_context_id, boolean context_stateful, byte[] final_context_token) {
        this.client_context_id = client_context_id;
        this.context_stateful = context_stateful;
        this.final_context_token = final_context_token;
    }
}

