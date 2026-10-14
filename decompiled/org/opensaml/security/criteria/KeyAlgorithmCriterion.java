/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.criteria;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class KeyAlgorithmCriterion
implements Criterion {
    private String keyAlgorithm;

    public KeyAlgorithmCriterion(@Nonnull String algorithm) {
        this.setKeyAlgorithm(algorithm);
    }

    @Nonnull
    public String getKeyAlgorithm() {
        return this.keyAlgorithm;
    }

    public void setKeyAlgorithm(@Nonnull String algorithm) {
        String trimmed = StringSupport.trimOrNull(algorithm);
        Constraint.isNotNull(trimmed, "Key algorithm criteria cannot be null or empty");
        this.keyAlgorithm = trimmed;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("KeyAlgorithmCriterion [keyAlgorithm=");
        builder.append(this.keyAlgorithm);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.keyAlgorithm.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof KeyAlgorithmCriterion) {
            return this.keyAlgorithm.equals(((KeyAlgorithmCriterion)obj).keyAlgorithm);
        }
        return false;
    }
}

