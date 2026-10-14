/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.apache.commons.codec.binary.Hex;

public final class X509SubjectKeyIdentifierCriterion
implements Criterion {
    private byte[] subjectKeyIdentifier;

    public X509SubjectKeyIdentifierCriterion(@Nonnull byte[] ski) {
        this.setSubjectKeyIdentifier(ski);
    }

    @Nonnull
    public byte[] getSubjectKeyIdentifier() {
        return this.subjectKeyIdentifier;
    }

    public void setSubjectKeyIdentifier(@Nonnull byte[] ski) {
        if (ski == null || ski.length == 0) {
            throw new IllegalArgumentException("Subject key identifier criteria value cannot be null or empty");
        }
        this.subjectKeyIdentifier = ski;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("X509SubjectKeyIdentifierCriterion [subjectKeyIdentifier=");
        builder.append(Hex.encodeHexString(this.subjectKeyIdentifier));
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.subjectKeyIdentifier.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof X509SubjectKeyIdentifierCriterion) {
            return this.subjectKeyIdentifier.equals(((X509SubjectKeyIdentifierCriterion)obj).subjectKeyIdentifier);
        }
        return false;
    }
}

