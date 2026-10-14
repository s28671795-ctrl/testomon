/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;

public final class MechandOptions
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String mechanism_type = "";
    public short options_supported;

    public MechandOptions() {
    }

    public MechandOptions(String mechanism_type, short options_supported) {
        this.mechanism_type = mechanism_type;
        this.options_supported = options_supported;
    }
}

