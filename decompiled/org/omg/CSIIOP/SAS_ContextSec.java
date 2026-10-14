/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSIIOP.ServiceConfiguration;

public final class SAS_ContextSec
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short target_supports;
    public short target_requires;
    public ServiceConfiguration[] privilege_authorities;
    public byte[][] supported_naming_mechanisms;
    public int supported_identity_types;

    public SAS_ContextSec() {
    }

    public SAS_ContextSec(short target_supports, short target_requires, ServiceConfiguration[] privilege_authorities, byte[][] supported_naming_mechanisms, int supported_identity_types) {
        this.target_supports = target_supports;
        this.target_requires = target_requires;
        this.privilege_authorities = privilege_authorities;
        this.supported_naming_mechanisms = supported_naming_mechanisms;
        this.supported_identity_types = supported_identity_types;
    }
}

