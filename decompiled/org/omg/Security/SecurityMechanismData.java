/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;

public final class SecurityMechanismData
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String mechanism = "";
    public String security_name = "";
    public short options_supported;
    public short options_required;

    public SecurityMechanismData() {
    }

    public SecurityMechanismData(String mechanism, String security_name, short options_supported, short options_required) {
        this.mechanism = mechanism;
        this.security_name = security_name;
        this.options_supported = options_supported;
        this.options_required = options_required;
    }
}

