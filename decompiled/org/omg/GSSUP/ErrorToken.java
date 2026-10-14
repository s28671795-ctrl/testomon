/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GSSUP;

import org.omg.CORBA.portable.IDLEntity;

public final class ErrorToken
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int error_code;

    public ErrorToken() {
    }

    public ErrorToken(int error_code) {
        this.error_code = error_code;
    }
}

