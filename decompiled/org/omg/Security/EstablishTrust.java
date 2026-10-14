/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;

public final class EstablishTrust
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public boolean trust_in_client;
    public boolean trust_in_target;

    public EstablishTrust() {
    }

    public EstablishTrust(boolean trust_in_client, boolean trust_in_target) {
        this.trust_in_client = trust_in_client;
        this.trust_in_target = trust_in_target;
    }
}

