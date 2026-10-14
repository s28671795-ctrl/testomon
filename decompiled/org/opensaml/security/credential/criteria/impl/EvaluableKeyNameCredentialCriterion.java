/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.opensaml.security.criteria.KeyNameCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableKeyNameCredentialCriterion
extends AbstractTriStatePredicate<Credential>
implements EvaluableCredentialCriterion {
    private final Logger log = LoggerFactory.getLogger(EvaluableKeyNameCredentialCriterion.class);
    private final String keyName;

    public EvaluableKeyNameCredentialCriterion(@Nonnull KeyNameCriterion criteria) {
        this.keyName = Constraint.isNotNull(criteria, "Criterion instance cannot be null").getKeyName();
    }

    public EvaluableKeyNameCredentialCriterion(@Nonnull String newKeyName) {
        String trimmed = StringSupport.trimOrNull(newKeyName);
        Constraint.isNotNull(trimmed, "Key name cannot be null or empty");
        this.keyName = trimmed;
    }

    @Override
    @Nullable
    public boolean apply(@Nullable Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return this.isNullInputSatisfies();
        }
        if (target.getKeyNames().isEmpty()) {
            this.log.info("Could not evaluate criteria, credential contained no key names");
            return this.isUnevaluableSatisfies();
        }
        return target.getKeyNames().contains(this.keyName);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvaluableKeyNameCredentialCriterion [keyName=");
        builder.append(this.keyName);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.keyName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EvaluableKeyNameCredentialCriterion) {
            return this.keyName.equals(((EvaluableKeyNameCredentialCriterion)obj).keyName);
        }
        return false;
    }
}

