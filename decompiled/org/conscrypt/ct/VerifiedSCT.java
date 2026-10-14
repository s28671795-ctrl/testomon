/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.ct;

import org.conscrypt.ct.SignedCertificateTimestamp;

public final class VerifiedSCT {
    public final SignedCertificateTimestamp sct;
    public final Status status;

    public VerifiedSCT(SignedCertificateTimestamp sct, Status status) {
        this.sct = sct;
        this.status = status;
    }

    public static enum Status {
        VALID,
        INVALID_SIGNATURE,
        UNKNOWN_LOG,
        INVALID_SCT;

    }
}

